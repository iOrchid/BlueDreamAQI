package org.zhiwei.aqi.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import org.zhiwei.aqi.entity.AQI
import org.zhiwei.aqi.entity.ItemStation
import org.zhiwei.libcore.LogKt
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

	val liveAQI = MutableLiveData<AQI>()


	private val stations = mutableListOf<ItemStation>()

	/**
	 * 请求网路数据
	 */
	fun serverData() {
		//mock data
		stations.add(ItemStation("万寿西宫", 6, 32))
		stations.add(ItemStation("定陵", 13, 32))
		stations.add(ItemStation("东四", 8, 33))
		stations.add(ItemStation("天坛", 11, 32))
		stations.add(ItemStation("农展馆", 9, 33))
		stations.add(ItemStation("官园", 8, 34))
		stations.add(ItemStation("海淀区万柳", 12, 33))
		stations.add(ItemStation("顺义新城", 29, 42))
		stations.add(ItemStation("怀柔镇", 9, 31))
		stations.add(ItemStation("昌平镇", 12, 31))
		stations.add(ItemStation("奥体中心", 12, 32))
		stations.add(ItemStation("古城", 9, 33))
		liveAQI.postValue(AQI("北京", "26日 11:00", 28, 8, "集团你宁阿济格的", "你哦决定了旮角", stations))

		//请求解析数据
		viewModelScope.launch(Dispatchers.IO) {
			val html = KtHttp.initConfig("http://m.pm25.com/wap/city/")
				.get("beijing.html").toBean(String::class.java)
			LogKt.i("ktHttp str $html")
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
