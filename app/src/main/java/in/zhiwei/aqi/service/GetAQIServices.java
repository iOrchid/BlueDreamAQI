package in.zhiwei.aqi.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.TimeUnit;

import in.zhiwei.aqi.R;
import in.zhiwei.aqi.entity.AQIModel;
import in.zhiwei.aqi.global.GlobalConstants;
import in.zhiwei.aqi.network.AQIService;
import in.zhiwei.aqi.network.HttpApi;
import in.zhiwei.aqi.utils.Tools;
import in.zhiwei.aqi.widgets.AqiWidget;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 用于后台定时获取aqi数据的service
 * Author: gzw48760.
 * Date: 2018/4/10 0010,19:48.
 */
public class GetAQIServices extends Service {
	private Disposable timerDispose;//计时任务的控制阀
	private Disposable aqiDispose;//网络请求的控制阀

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String city = SPUtils.getInstance().getString(GlobalConstants.SP_KEY_CURRENT_CITY_ID, "beijing");
		//间隔定时任务
		timerDispose = Observable.interval(1, 30, TimeUnit.SECONDS)
				.subscribeOn(Schedulers.io())
				.subscribe(aLong -> {
					//请求aqi
					getServerAQI(city);
				});
//		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//		int five = 5000; // 这是5s
//		long triggerAtTime = SystemClock.elapsedRealtime() + five;
//		Intent i = new Intent(this, AlarmReceiver.class);
//		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		if (timerDispose != null) {
			timerDispose.dispose();
		}
		if (aqiDispose != null) {
			aqiDispose.dispose();
		}
		super.onDestroy();
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
		aqiDispose = aqiService.getAQIServerHtmlStr(city, language)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::parserHtml);
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
		//更新widget
		String updateTime = aqiModel.getTime().getS().getEn().getTime();//先默认英文的时间，如果是是中文环境，在重新赋值
		AQIModel.TimeBean.SBean.CnBean cnBean = aqiModel.getTime().getS().getCn();
		if (Tools.isChinese()) {
			updateTime = cnBean == null ? Tools.translateEn(updateTime) : cnBean.getTime();
		}

		// 更新widget
		int aqi = aqiModel.getAqi();
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.aqi_widget);
		views.setTextViewText(R.id.tv_aqi_widget, aqi + "");
		updateTime = updateTime.substring(updateTime.length() - 6, updateTime.length());
		views.setTextViewText(R.id.tv_city_widget, aqiModel.getCity().getName() + updateTime);
		views.setInt(R.id.rl_root_widget, "setBackgroundResource", Tools.getAQILevelBG(aqi));
		// 更新widget
		ComponentName componentName = new ComponentName(this, AqiWidget.class);
		AppWidgetManager.getInstance(this).updateAppWidget(componentName, views);
	}

	/**
	 * 绑定service
	 *
	 * @param intent intent
	 * @return binder对象
	 */
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		//此处，不需要使用绑定服务，所以不实现
		return null;
	}
}
