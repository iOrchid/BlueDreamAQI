package org.zhiwei.aqi.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.zhiwei.aqi.entity.Pm25AQI
import org.zhiwei.aqi.utils.ParserUtils
import org.zhiwei.libnet.KtHttp

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
 */
class MainViewModel : ViewModel() {

	val liveAQI = MutableLiveData<Pm25AQI>()

	//监控站点
	private val stations = mutableListOf<Pm25AQI.ItemStation>()

	/**
	 * 请求网路数据
	 */
	fun pm25Server() {

		//请求解析数据
		viewModelScope.launch(Dispatchers.IO) {
			val html = KtHttp.initConfig("http://m.pm25.com/wap/city/")
				.get("xian.html").toBean(String::class.java)
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
			liveAQI.postValue(
				Pm25AQI(
					city,
					updateTime,
					aqiNum.toInt(),
					concentration,
					"http://m.pm25.com$imgUrl",
					todayDesc,
					tips,
					stations
				)
			)
		}
	}

	/**
	 * 将Response的对象，转化为需要的对象类型，也就是将body.string转为bean
	 * [clazz] 待转化的对象类型
	 * @return 返回需要的类型对象，可能为null，如果json解析失败的话
	 */
	@Suppress("UNCHECKED_CAST")
	fun <T> Response.toBean(clazz: Class<T>): T? {
		if (clazz.isAssignableFrom(String::class.java)) {
			return this.body?.string() as T
		}
		return kotlin.runCatching {
			Gson().fromJson(this.body?.string(), clazz)
		}.onFailure { e ->
			e.printStackTrace()
		}.getOrNull()
	}
}