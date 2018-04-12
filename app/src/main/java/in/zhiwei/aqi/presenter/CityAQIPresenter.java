package in.zhiwei.aqi.presenter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import in.zhiwei.aqi.R;
import in.zhiwei.aqi.contract.IAQIContract;
import in.zhiwei.aqi.entity.AQIEntity;
import in.zhiwei.aqi.entity.AQIModel;
import in.zhiwei.aqi.entity.OtherAQIData;
import in.zhiwei.aqi.global.GlobalConstants;
import in.zhiwei.aqi.network.AQIService;
import in.zhiwei.aqi.network.HttpApi;
import in.zhiwei.aqi.utils.Tools;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 城市aqi数据控制presenter
 * Author: gzw48760.
 * Date: 2018/2/6 0006,10:59.
 */

public class CityAQIPresenter implements IAQIContract.IAQIPresenter {

	private IAQIContract.IAQIView mAQIView;//AQIView的对象
	private String apkUrl;//apk下载url
	private String updateInfo;//升级信息
	private Disposable disposable;

    /**
     * presenter
     * 构造函数
     *
     * @param view iAQIView的对象
     */
    public CityAQIPresenter(IAQIContract.IAQIView view) {
        this.mAQIView = view;
    }

	@Override
	public void start() {
		//用于初始化配置
		String city = SPUtils.getInstance().getString(GlobalConstants.SP_KEY_CURRENT_CITY_ID, "beijing");
		getServerAQI(city);
	}

    /**
     * 获取指定城市的aqi返回html
     *
     * @param city 城市string
     */
    private void getServerAQI(@NonNull String city) {
        String language = Tools.getSupportedLanguage();
        getServerAQI(city, language);
    }

	/**
	 * 指定城市，语言，获取aqi的html返回
	 *
	 * @param city     城市，string类型
	 * @param language 语言，cn，en等
	 */
	private void getServerAQI(@NonNull String city, @NonNull String language) {
		AQIService aqiService = HttpApi.getInstance().create(AQIService.class);
		disposable = aqiService.getAQIServerHtmlStr(city, language)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::parserHtml, throwable -> mAQIView.onGetAQIFailed(throwable.getMessage()));
	}

    /**
     * 解析html数据
     *
     * @param server 原始的html的string
     */
    public void parserHtml(String server) {
        Document html = Jsoup.parse(server);
        Element pageMain = html.getElementById("waPageMain");//获取main的div
        Elements waPageContent = pageMain.getElementsByClass("waPageContent");//获取content的div
        Element waContent = waPageContent.get(0);//第一个content的div是主要数据
        Element header = waContent.getElementById("header");//获取header的div
        //温度，风速，风向 的div
        OtherAQIData otherAQIData = new OtherAQIData();
        Element windElement = header.getElementsByIndexEquals(4).get(0);
        String windy = windElement.toString();
        windy = windy.substring(windy.length() - 25).replaceAll("[\\r\\n ]", "");
        String reg = "</b>m/s([\\s\\S]*?)-</div>";
        List<String> matched = Tools.getMatched(reg, windy);
        if (!matched.isEmpty()) {
            String windDirection = matched.get(0).replaceAll("</b>m/s", "").replaceAll("-</div>", "");
            otherAQIData.setWindDirection(windDirection);
        }
        //获取script脚本中的iaqi数据
        Element text = waContent.selectFirst("script");
        String[] functions = text.data().split("function");
        String substring = "";
        Gson gson = new Gson();
        for (String function : functions) {
            if (function.contains("getAqiModel")) {
                substring = function.substring(63, function.length() - 15);
            }
        }
        AQIModel aqiModel = gson.fromJson(substring, AQIModel.class);
        //将model 和otherData封装到entity中
        AQIEntity entity = new AQIEntity();
        entity.setAqiModel(aqiModel);
        entity.setOtherAQIData(otherAQIData);
        //回调到UI填充数据
        mAQIView.onGetAQISuccess(entity);
        //将数据缓存到本地
        SPUtils.getInstance().put(GlobalConstants.SP_KEY_AQI_SERVER_DATA,server);
    }

    /**
     * 切换站点，更新数据
     *
     * @param station city 名称
     */
    public void changeStation(@NonNull String station) {
        getServerAQI(station);
    }

    /**
     * 检查升级
     */
    public void checkUpgrade(@NonNull Context context) {
        //清空已有的下载文件
        FileUtils.deleteAllInDir(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        //请求网络，获得返回json
        HttpApi.getInstance().create(AQIService.class)
                .checkUpgrade()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(upgradeRes -> {
                            int localVersion = AppUtils.getAppVersionCode();
                            int versionNum = upgradeRes.getVersionNum();
                            mAQIView.onCheckUpgradeSuccess(localVersion < versionNum);
                            apkUrl = upgradeRes.getUpdateUrl();
                            updateInfo = upgradeRes.getUpgradeInfo();
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            mAQIView.onCheckUpgradeFailed(throwable.getMessage());
                        });
    }
    /**
     * 下载app
     * @param context context
     */
    public void downloadApk(@NonNull Context context) {
        initDownLoad(context, apkUrl, updateInfo);
    }

    /**
     * 初始化下载配置
     *
     * @param updateUrl 下载apk的链接url
     */
    private void initDownLoad(Context context, String updateUrl, String upgradeInfo) {
        //1.得到下载对象
        DownloadManager dowanloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //2.创建下载请求对象，并且把下载的地址放进去
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateUrl));
        //3.给下载的文件指定路径
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "aqi.apk");
        //4.设置显示在文件下载Notification（通知栏）中显示的文字。6.0的手机Description不显示
        request.setTitle(context.getString(R.string.app_name));
        request.setDescription(upgradeInfo);
        //5更改服务器返回的minetype为android包类型
        request.setMimeType("application/vnd.android.package-archive");
        //6.设置在什么连接状态下执行下载操作
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //7. 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        //8. 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        long lastDownloadId = dowanloadmanager != null ? dowanloadmanager.enqueue(request) : 0L;
        //9.保存id到缓存
        SPUtils.getInstance().put(GlobalConstants.SP_KEY_DOWNLOAD_ID, lastDownloadId);
        //10.采用内容观察者模式实现进度
//        downloadObserver = new DownloadChangeObserver(null);
//        context.getContentResolver().registerContentObserver(CONTENT_URI, true, downloadObserver);
    }

	/**
	 * 关闭资源
	 */
	public void dispose() {
		if (disposable != null) {
			disposable.dispose();
		}
	}
}
