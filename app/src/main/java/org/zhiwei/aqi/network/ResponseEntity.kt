package org.zhiwei.aqi.network

import androidx.annotation.Keep


/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月28日 11:24
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 响应返回的数据类
 */

//region pm25.com

/**
 * 通过ip的方式，获取粗略定位城市，可能存在偏差，或者是归属省市
 */
@Keep
data class NearestCityRsp(
	val d: List<D?>?,
	val dt: String?,
	val g: G?
) {
	/*
	 *
{"d":[{"nlo":"Suzhou","nna":"苏州","t":1588039200,"pol":"pm25","x":"1489","v":"74","u":"Suzhou","key":"_Cy6tysgvBQA","d":1.1,"geo":[31.298886,120.585316],"cca2":"CN"}],"dt":"248.431µs","g":{"ip":"58.211.111.18","city":"Suzhou","country":"China","names":{"de":"Suzhou","en":"Suzhou","es":"Suzhou","fr":"Suzhou","ja":"蘇州市","pt-BR":"Suzhou","ru":"Сучжоу","zh-CN":"苏州"},"iso2":"CN","geo":[31.3041,120.5954]}}
	 */

	@Keep
	data class D(
		val cca2: String?,
		val d: Double,
		val geo: List<Double>?,
		val key: String?,
		val nlo: String?,
		val nna: String?,
		val pol: String?,
		val t: Int,
		val u: String?,
		val v: String?,
		val x: String?
	)

	@Keep
	data class G(
		val city: String?,
		val country: String?,
		val geo: List<Double>?,
		val ip: String?,
		val iso2: String?,
		val names: Names?
	) {
		@Keep
		data class Names(
			val de: String?,
			val en: String?,
			val es: String?,
			val fr: String?,
			val ja: String?,
			val `pt-BR`: String?,
			val ru: String?,
			val `zh-CN`: String?
		)
	}
}

//endregion


//region aqicn.org


//endregion