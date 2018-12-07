package `in`.zhiwei.aqi.service

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.entity.AQIModel
import `in`.zhiwei.aqi.global.GlobalConstants
import `in`.zhiwei.aqi.network.AQIService
import `in`.zhiwei.aqi.network.HttpApi
import `in`.zhiwei.aqi.utils.Tools
import `in`.zhiwei.aqi.widgets.AqiWidget
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.util.concurrent.TimeUnit

/**
 * 用于后台定时获取aqi数据的service，定时来更新桌面widget
 * Author: gzw48760.
 * Date: 2018/4/10 0010,19:48.
 */
class GetAQIServices : Service() {
    private var timerDispose: Disposable? = null//计时任务的控制阀
    private var aqiDispose: Disposable? = null//网络请求的控制阀

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val city = SPUtils.getInstance().getString(GlobalConstants.SP_KEY_CURRENT_CITY_ID, "beijing")
        //间隔定时任务
        timerDispose = Observable.interval(1, 30, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe { aLong ->
                //请求aqi
                getServerAQI(city)
            }
        return Service.START_STICKY
    }

    override fun onDestroy() {
        if (timerDispose != null) {
            timerDispose!!.dispose()
        }
        if (aqiDispose != null) {
            aqiDispose!!.dispose()
        }
        super.onDestroy()
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
        aqiDispose = aqiService.getAQIServerHtmlStr(city, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ this.parserHtml(it) }, { throwable ->
                throwable.printStackTrace()
                Log.d("GetAQIServices", throwable.message)
            })
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
        //更新widget
        var updateTime = aqiModel.time!!.s!!.en!!.time//先默认英文的时间，如果是是中文环境，在重新赋值
        val cnBean = aqiModel.time!!.s!!.cn
        if (Tools.isChinese) {
            updateTime = if (cnBean == null) Tools.translateEn(updateTime!!) else cnBean.time
        }

        // 更新widget
        val aqi = aqiModel.aqi
        val views = RemoteViews(packageName, R.layout.aqi_widget)
        views.setTextViewText(R.id.tv_aqi_widget, aqi.toString() + "")
        updateTime = updateTime!!.substring(updateTime.length - 6, updateTime.length)
        views.setTextViewText(R.id.tv_city_widget, aqiModel.city!!.name!! + updateTime)
        views.setInt(R.id.rl_root_widget, "setBackgroundResource", Tools.getAQILevelBG(aqi))
        // 更新widget
        val componentName = ComponentName(this, AqiWidget::class.java)
        AppWidgetManager.getInstance(this).updateAppWidget(componentName, views)
    }

    /**
     * 绑定service
     *
     * @param intent intent
     * @return binder对象
     */
    override fun onBind(intent: Intent): IBinder? {
        //此处，不需要使用绑定服务，所以不实现
        return null
    }
}
