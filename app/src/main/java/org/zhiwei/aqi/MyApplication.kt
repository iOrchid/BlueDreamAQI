package org.zhiwei.aqi

import android.app.Application
import androidx.work.*
import com.didichuxing.doraemonkit.DoraemonKit
import org.zhiwei.aqi.workers.QueryAqiWorker
import org.zhiwei.aqi.workers.QueryCityWorker
import java.util.concurrent.TimeUnit

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月25日 22:00
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 自定义Application
 */
class MyApplication : Application() {

	override fun onCreate() {
		super.onCreate()
		DoraemonKit.install(this)

		//后台任务
//		workTask()
	}

	/**
	 * 后台worker
	 */
	private fun workTask() {
		val localCityRequest = OneTimeWorkRequestBuilder<QueryCityWorker>().build()
		val aqiRequest = OneTimeWorkRequestBuilder<QueryAqiWorker>().build()

		val aqiWorker =
			PeriodicWorkRequest.Builder(QueryAqiWorker::class.java, 30, TimeUnit.MINUTES)
				//设置重试机制的时间策略，有线性和指数增长两种方式
				.setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.MINUTES)
				.build()
		//单次获取粗略定位信息和执行一次aqi数据查询
		WorkManager.getInstance(this)
			.beginWith(localCityRequest)
			.then(aqiRequest)
			.enqueue()
		//定时轮询aqi的后台任务，
		WorkManager.getInstance(this)
			.enqueueUniquePeriodicWork("queryAqi", ExistingPeriodicWorkPolicy.KEEP, aqiWorker)
	}
}