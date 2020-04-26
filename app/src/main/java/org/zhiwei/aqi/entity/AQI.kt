package org.zhiwei.aqi.entity

import androidx.annotation.Keep

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月26日 15:32
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
@Keep
data class ItemStation(val name: String, val pm2: Int, val aqi: Int)

/**
 * 首页的aqi数据对象类
 */
@Keep
data class AQI(
	val city: String,
	val lastUpdateTime: String,
	val aqi: Int,
	val pm2: Int,
	val todayDesc: String,
	val tips: String,
	val stations: List<ItemStation>
)