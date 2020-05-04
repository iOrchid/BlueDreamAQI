package org.zhiwei.aqi.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.zhiwei.aqi.entity.Pm25AQI
import org.zhiwei.aqi.network.Pm25Service
import org.zhiwei.aqi.utils.ParserUtils
import org.zhiwei.libcore.LogKt
import org.zhiwei.libnet.KtHttp
import org.zhiwei.libnet.KtRetrofit
import org.zhiwei.libnet.support.toEntity
import retrofit2.await

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年05月01日 22:52
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 后台服务运行workers
 */

/**
 * 根据网络ip的方式，错略的获取当前城市地区，用于后续的aqi数据查询
 */
class QueryCityWorker(
	context: Context,
	workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

	override suspend fun doWork(): Result = coroutineScope {
		val await = KtRetrofit.initConfig("http://m.pm25.com/")
			.getService(Pm25Service::class.java)
			.nearCity().await()
		LogKt.d("doWork 43: await $await")
		val data = Data.Builder()
			.putString(DATA_KEY_CITY, await.g?.city)
			.build()
		Result.success(data)
	}

}

/**
 * 查询城市的aqi数据
 */
class QueryAqiWorker(
	context: Context,
	workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

	override suspend fun doWork(): Result = coroutineScope {
		val cityPinyin = inputData.getString(DATA_KEY_CITY)
		LogKt.d("doWork 63: city $cityPinyin")

		val rsp = KtHttp.initConfig("http://m.pm25.com/wap/city/")
			.get("$cityPinyin.html")
		//请求成功才继续
		val stations = mutableListOf<Pm25AQI.ItemStation>()

		if (rsp?.isSuccessful == true) {
			val html = rsp.toEntity<String>()
			val doc = Jsoup.parse(html)
			val city = doc.getElementsByClass("cm_location")[0].text()
			val updateTime = doc.getElementsByClass("cm_updatetime")[0].text()
			val aqiNum = doc.getElementsByClass("cm_area_big")[0].text()
			val concentration = doc.getElementsByClass("cm_nongdu")[0].text()
			val todayDesc = doc.getElementsByClass("c_item")[0].select("p").first().text()
			val tips = doc.getElementsByClass("c_item")[1].select("p").first().text()
			val bgImg = doc.getElementsByClass("c_bg").elementAt(0).attr("style")
			val imgUrl = ParserUtils.parseInBracketsFirst(bgImg)//相对路径
			val list = doc.getElementsByClass("ci_jiance_line")
			list.forEach { element: Element? ->
				element?.apply {
					val station = getElementsByClass("ci_location").first().text()
					val pm25 = getElementsByClass("ci_pm25num").first().text()
					val aqi = getElementsByTag("strong").first().text()
					stations.add(Pm25AQI.ItemStation(station, pm25.toInt(), aqi.toInt()))
				}
			}
			val pm25AQI = Pm25AQI(
				city,
				updateTime,
				aqiNum.toInt(),
				concentration,
				"http://m.pm25.com$imgUrl",
				todayDesc,
				tips,
				stations
			)
			val data = Data.Builder()
				.putString(DATA_KEY_PM25_AQI, pm25AQI.toString())
				.build()
			Result.success(data)
		} else {
			Result.failure()
		}
	}

}

//data的key
const val DATA_KEY_CITY = "worker_data_key_city"
const val DATA_KEY_PM25_AQI = "worker_data_key_pm25_aqi"