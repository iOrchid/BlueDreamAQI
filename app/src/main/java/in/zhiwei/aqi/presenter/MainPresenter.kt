package `in`.zhiwei.aqi.presenter

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.contract.IAQIContract
import `in`.zhiwei.aqi.entity.AQIEntity
import `in`.zhiwei.aqi.entity.AQIModel
import `in`.zhiwei.aqi.entity.OtherAQIData
import `in`.zhiwei.aqi.global.GlobalConstants
import `in`.zhiwei.aqi.network.AQIService
import `in`.zhiwei.aqi.network.HttpApi
import `in`.zhiwei.aqi.utils.Tools
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup

/**
 * 城市aqi数据控制presenter
 * Author: gzw48760.
 * Date: 2018/2/6 0006,10:59.
 */

class MainPresenter
/**
 * presenter
 * 构造函数
 *
 * @param view iAQIView的对象
 */
    (
    private val mAQIView: IAQIContract.IAQIView//AQIView的对象
) : IAQIContract.IAQIPresenter {
    private var apkUrl: String? = null//apk下载url
    private var updateInfo: String? = null//升级信息
    private var disposable: Disposable? = null//请求aqi的retrofit的网络控制阀
    private var disposableUpgrade: Disposable? = null//检查升级的disposable

    override fun start() {
        //用于初始化配置
        val city = SPUtils.getInstance().getString(GlobalConstants.SP_KEY_CURRENT_CITY_ID, "beijing")
        getServerAQI(city)
    }

    /**
     * 获取指定城市的aqi返回html
     *
     * @param city 城市string
     */
    private fun getServerAQI(city: String) {
        val language = Tools.supportedLanguage
        getServerAQI(city, language)
    }

    /**
     * 指定城市，语言，获取aqi的html返回
     *
     * @param city     城市，string类型
     * @param language 语言，cn，en等
     */
    private fun getServerAQI(city: String, language: String) {
        val aqiService = HttpApi.instance.create(AQIService::class.java)
        disposable = aqiService.getAQIServerHtmlStr(city, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { this.parserHtml(it) },
                { throwable -> mAQIView.onGetAQIFailed(throwable.message!!) })
    }

    /**
     * 解析html数据
     *
     * @param server 原始的html的string
     */
    fun parserHtml(server: String) {
        val html = Jsoup.parse(server)
        val pageMain = html.getElementById("waPageMain")//获取main的div
        val waPageContent = pageMain.getElementsByClass("waPageContent")//获取content的div
        val waContent = waPageContent[0]//第一个content的div是主要数据
        val header = waContent.getElementById("header")//获取header的div
        //温度，风速，风向 的div
        val otherAQIData = OtherAQIData()
        val windElement = header.getElementsByIndexEquals(4)[0]
        var windy = windElement.toString()
        windy = windy.substring(windy.length - 25).replace("[\\r\\n ]".toRegex(), "")
        val reg = "</b>m/s([\\s\\S]*?)-</div>"
        val matched = Tools.getMatched(reg, windy)
        if (!matched.isEmpty()) {
            val windDirection = matched[0].replace("</b>m/s".toRegex(), "").replace("-</div>".toRegex(), "")
            otherAQIData.windDirection = windDirection
        }
        //获取script脚本中的iaqi数据
        val text = waContent.selectFirst("script")
        val functions = text.data().split("function".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var substring = ""
        val gson = Gson()
        for (function in functions) {
            if (function.contains("getAqiModel")) {
                substring = function.substring(63, function.length - 15)
            }
        }
        val aqiModel = gson.fromJson(substring, AQIModel::class.java)
        //将model 和otherData封装到entity中
        val entity = AQIEntity()
        entity.aqiModel = aqiModel
        entity.otherAQIData = otherAQIData
        //回调到UI填充数据
        mAQIView.onGetAQISuccess(entity)
        //将数据缓存到本地
        SPUtils.getInstance().put(GlobalConstants.SP_KEY_AQI_SERVER_DATA, server)
    }

    /**
     * 切换站点，更新数据
     *
     * @param station city 名称
     */
    fun changeStation(station: String) {
        getServerAQI(station)
    }

    /**
     * 检查升级
     */
    fun checkUpgrade(context: Context) {
        //清空已有的下载文件
        FileUtils.deleteAllInDir(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))
        //请求网络，获得返回json
        disposableUpgrade = HttpApi.instance.create(AQIService::class.java)
            .checkUpgrade()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ upgradeRes ->
                val localVersion = AppUtils.getAppVersionCode()
                val versionNum = upgradeRes.versionNum
                val forceUpgrade = upgradeRes.isForceUpgrade
                val hasNewVersion = localVersion < versionNum
                mAQIView.onCheckUpgradeSuccess(hasNewVersion && forceUpgrade)
                apkUrl = upgradeRes.updateUrl
                updateInfo = upgradeRes.upgradeInfo
            },
                { throwable ->
                    throwable.printStackTrace()
                    mAQIView.onCheckUpgradeFailed(throwable.message!!)
                })
    }

    /**
     * 下载app
     *
     * @param context context
     */
    fun downloadApk(context: Context) {
        initDownLoad(context, apkUrl, updateInfo)
    }

    /**
     * 初始化下载配置
     *
     * @param updateUrl 下载apk的链接url
     */
    private fun initDownLoad(context: Context, updateUrl: String?, upgradeInfo: String?) {
        //1.得到下载对象
        val dowanloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        //2.创建下载请求对象，并且把下载的地址放进去
        val request = DownloadManager.Request(Uri.parse(updateUrl))
        //3.给下载的文件指定路径
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "aqi.apk")
        //4.设置显示在文件下载Notification（通知栏）中显示的文字。6.0的手机Description不显示
        request.setTitle(context.getString(R.string.app_name))
        request.setDescription(upgradeInfo)
        //5更改服务器返回的minetype为android包类型
        request.setMimeType("application/vnd.android.package-archive")
        //6.设置在什么连接状态下执行下载操作
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
        //7. 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner()
        //8. 设置为可见和可管理
        request.setVisibleInDownloadsUi(true)
        val lastDownloadId = dowanloadmanager?.enqueue(request) ?: 0L
        //9.保存id到缓存
        SPUtils.getInstance().put(GlobalConstants.SP_KEY_DOWNLOAD_ID, lastDownloadId)
        //10.采用内容观察者模式实现进度
        //        downloadObserver = new DownloadChangeObserver(null);
        //        context.getContentResolver().registerContentObserver(CONTENT_URI, true, downloadObserver);
    }

    /**
     * 关闭资源
     */
    fun dispose() {
        if (disposable != null) {
            disposable!!.dispose()
        }
        if (disposableUpgrade != null) {
            disposableUpgrade!!.dispose()
        }
    }
}
