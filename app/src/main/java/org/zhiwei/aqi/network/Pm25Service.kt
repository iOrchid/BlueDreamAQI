package org.zhiwei.aqi.network

import retrofit2.Call
import retrofit2.http.GET

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月28日 11:21
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Pm25.com的数据接口retrofit 的 service
 * aqicn.org的数据接口的retrofit 的service
 */
interface Pm25Service {

	//region pm25.com

	//endregion


	//region aqicn.org

	@GET("https://api.waqi.info/mapq/nearest")
	fun nearCity(): Call<NearestCityRsp>

	//endregion

}