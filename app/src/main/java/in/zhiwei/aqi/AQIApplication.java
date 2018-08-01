package in.zhiwei.aqi;

import android.app.Application;
import android.content.Intent;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.blankj.utilcode.util.Utils;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.squareup.leakcanary.LeakCanary;

import in.zhiwei.aqi.service.GetAQIServices;
import in.zhiwei.aqi.service.LocationService;

/**
 * App的Application，用于初始化基础数据配置
 * Author: gzw48760.
 * Date: 2018/2/1 0001,14:39.
 */

public class AQIApplication extends Application {

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
		initFeedbackService();
	}

	/**
	 * 意见反馈的初始化配置
	 */
	private void initFeedbackService() {
		//默认初始化
		FeedbackAPI.init(this);
		//沉浸式任务栏，控制台设置为true之后此方法才能生效
		FeedbackAPI.setTranslucent(true);
		//设置标题栏"历史反馈"的字号，需要将控制台中此字号设置为0
		FeedbackAPI.setHistoryTextSize(16);
		//设置标题栏高度，单位为像素
		FeedbackAPI.setTitleBarHeight(100);
	}
}
