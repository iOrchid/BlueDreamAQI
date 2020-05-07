package org.zhiwei.aqi.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.zhiwei.aqi.entity.Pm25AQI
import org.zhiwei.aqi.network.NearestCityRsp
import org.zhiwei.aqi.utils.ParserUtils
import org.zhiwei.libcore.LogKt
import org.zhiwei.libnet.KtHttp
import org.zhiwei.libnet.support.toEntity

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

	//监控站点
	private val stations = mutableListOf<Pm25AQI.ItemStation>()

	//loading
	val isLoading = MutableLiveData<Boolean>()

	//城市的aqi
	val liveAQI = MutableLiveData<Pm25AQI>()

	//城市
	val liveCity = MutableLiveData<String>()

	/**
	 * 通过ip查询就近的城市，这个定位很粗泛，一般就是手机卡的归属省市，或者地区
	 */
	fun nearCity() {
		viewModelScope.launch {
			val url = "https://api.waqi.info/mapq/nearest"
			val rsp = KtHttp.initConfig(url).get("")
			//请求成功才继续
			if (!rsp.isSuccessful) return@launch

			val toBean = rsp.toEntity<NearestCityRsp>()
			val city = toBean?.g?.city
			liveCity.postValue(city)
			LogKt.d("nearCity 44行: $city")
		}
	}


	/**
	 * 请求网路数据
	 * [cityPinyin] 城市的拼音
	 */
	fun pm25Server(cityPinyin: String) {
		isLoading.postValue(true)
		//请求解析数据
		viewModelScope.launch(Dispatchers.IO) {
			val rsp = KtHttp.initConfig("http://m.pm25.com/wap/city/")
				.get("$cityPinyin.html")
			//loading finish
			isLoading.postValue(false)
			//请求成功才继续
			if (!rsp.isSuccessful) return@launch

			val html = rsp.toEntity<String>()
			val doc = Jsoup.parse(html)
			kotlin.runCatching {
				val elementsByClass = doc.getElementsByClass("cm_location")
				if (elementsByClass.size < 1) return@runCatching
				val city = elementsByClass[0].text()
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
			}.onFailure {
				//pm25 移动端可能不支持该城市的数据
				it.printStackTrace()
			}
		}
	}

	/**
	 * 刷新数据
	 */
	fun refresh() {
		pm25Server(liveCity.value ?: "beijing")
	}
}
