package in.zhiwei.aqi;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;
import com.blankj.utilcode.util.Utils;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.squareup.leakcanary.LeakCanary;
import com.ut.mini.internal.UTTeamWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.zhiwei.aqi.service.GetAQIServices;
import in.zhiwei.aqi.service.LocationService;
import in.zhiwei.aqi.utils.Tools;

/**
 * App的Application，用于初始化基础数据配置
 * Author: gzw48760.
 * Date: 2018/2/1 0001,14:39.
 */

public class AQIApplication extends Application {

	private static final String TAG = "AQIApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		//初始化配置
		Utils.init(this);
		// 添加中文城市词典
		Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));
		LeakCanary.install(this);
		//启动定位服务
		Intent intent = new Intent(this, LocationService.class);
		startService(intent);
		//启动定时服务
		Intent timerIntent = new Intent(this, GetAQIServices.class);
		startService(timerIntent);
		//初始化各类配置
		initFeedbackService();//意见反馈
//		initHttpDnsService();
		initManService();//应用统计
//		initPushService(this);
	}

	/**
	 * 初始化Mobile Analytics服务
	 */
	private void initManService() {
		// 获取MAN服务
		MANService manService = MANServiceProvider.getService();
		// 打开调试日志
		manService.getMANAnalytics().turnOnDebug();
		manService.getMANAnalytics().setAppVersion(Tools.getAppVersion());
		// MAN初始化方法之一，通过插件接入后直接在下发json中获取appKey和appSecret初始化
		manService.getMANAnalytics().init(this, getApplicationContext());
		// MAN另一初始化方法，手动指定appKey和appSecret
		// String appKey = "******";
		// String appSecret = "******";
		// manService.getMANAnalytics().init(this, getApplicationContext(), appKey, appSecret);
		// 若需要关闭 SDK 的自动异常捕获功能可进行如下操作,详见文档5.4
		//manService.getMANAnalytics().turnOffCrashReporter();
		// 通过此接口关闭页面自动打点功能，详见文档4.2
//		manService.getMANAnalytics().turnOffAutoPageTrack();
		// 设置渠道（用以标记该app的分发渠道名称），如果不关心可以不设置即不调用该接口，渠道设置将影响控制台【渠道分析】栏目的报表展现。如果文档3.3章节更能满足您渠道配置的需求，就不要调用此方法，按照3.3进行配置即可
//		manService.getMANAnalytics().setChannel("某渠道");
		// 若AndroidManifest.xml 中的 android:versionName 不能满足需求，可在此指定；
		// 若既没有设置AndroidManifest.xml 中的 android:versionName，也没有调用setAppVersion，appVersion则为null
		//manService.getMANAnalytics().setAppVersion("2.0");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("debug_api_url", "https://service-usertrack.alibaba-inc.com/upload_records_from_client");
		map.put("debug_key", "aliyun_sdk_utDetection");
		map.put("debug_sampling_option", "true");
		UTTeamWork.getInstance().turnOnRealTimeDebug(map);
	}

	/**
	 * 意见反馈的初始化配置
	 */
	private void initFeedbackService() {
		/**
		 * 添加自定义的error handler
		 */
		FeedbackAPI.addErrorCallback((context, errorMessage, code) -> Toast.makeText(context, "ErrMsg is: " + errorMessage, Toast.LENGTH_SHORT).show());
		FeedbackAPI.addLeaveCallback(() -> {
			Log.d("DemoApplication", "custom leave callback");
			return null;
		});
		/**
		 * 建议放在此处做初始化
		 */
		//默认初始化
		FeedbackAPI.init(this);
		//FeedbackAPI.init(this, "DEFAULT_APPKEY", "DEFAULT_APPSECRET");
		/**
		 * 在Activity的onCreate中执行的代码
		 * 可以设置状态栏背景颜色和图标颜色，这里使用com.githang:status-bar-compat来实现
		 */
		//FeedbackAPI.setActivityCallback(new IActivityCallback() {
		//    @Override
		//    public void onCreate(Activity activity) {
		//        StatusBarCompat.setStatusBarColor(activity,getResources().getColor(R.color.aliwx_setting_bg_nor),true);
		//    }
		//});
		/**
		 * 自定义参数演示
		 */
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("loginTime", "登录时间");
			jsonObject.put("visitPath", "登陆，关于，反馈");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		FeedbackAPI.setAppExtInfo(jsonObject);
		/**
		 * 以下是设置UI
		 */
		//设置默认联系方式
		FeedbackAPI.setDefaultUserContactInfo("13800000000");
		//沉浸式任务栏，控制台设置为true之后此方法才能生效
		FeedbackAPI.setTranslucent(true);
		//设置返回按钮图标
		//FeedbackAPI.setBackIcon(R.drawable.ali_feedback_common_back_btn_bg);
		//设置标题栏"历史反馈"的字号，需要将控制台中此字号设置为0
		FeedbackAPI.setHistoryTextSize(20);
		//设置标题栏高度，单位为像素
		FeedbackAPI.setTitleBarHeight(100);
	}

//	/**
//	 * Http Dns 的初始化配置
//	 */
//	private void initHttpDnsService() {
//		// 初始化httpdns
//		//HttpDnsService httpdns = HttpDns.getService(getApplicationContext(), accountID);
//		HttpDnsService httpdns = HttpDns.getService(getApplicationContext());
//		//this.setPreResoveHosts();
//		// 允许过期IP以实现懒加载策略
//		//httpdns.setExpiredIPEnabled(true);
//	}
//
//	/**
//	 * 初始化云推送通道
//	 *
//	 * @param applicationContext
//	 */
//	private void initPushService(final Context applicationContext) {
//		PushServiceFactory.init(applicationContext);
//		final CloudPushService pushService = PushServiceFactory.getCloudPushService();
//		pushService.register(applicationContext, new CommonCallback() {
//			@Override
//			public void onSuccess(String response) {
//				Log.i(TAG, "init cloudchannel success");
//				//setConsoleText("init cloudchannel success");
//			}
//
//			@Override
//			public void onFailed(String errorCode, String errorMessage) {
//				Log.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
//				//setConsoleText("init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
//			}
//		});
//	}
}
