package org.zhiwei.aqi.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.zhiwei.aqi.R

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月26日 16:08
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * dataBinding的xml绑定适配类
 */
object BindUtils {

	/**
	 * aqi的质量等级描述
	 */
	@JvmStatic
	fun aqiLevelStr(aqi: Int): String {
		return when (aqi) {
			in 0..50 -> "优"
			in 51..100 -> "良"
			in 101..150 -> "轻度"
			in 151..200 -> "中度"
			in 201..300 -> "重度"
			in 301..500 -> "严重"
			else -> "严重"
		}
	}

	/**
	 * 根据不同的aqi 等级，使用不同的背景图片资源
	 */
	@JvmStatic
	fun aqiLevelRes(aqi: Int): Int {
		return when (aqi) {
			in 0..50 -> R.drawable.shape_air_good
			in 50..100 -> R.drawable.shape_air_moderate
			in 100..150 -> R.drawable.shape_air_usg
			in 150..200 -> R.drawable.shape_air_unhealthy
			in 200..300 -> R.drawable.shape_air_very_unhealthy
			in 300..500 -> R.drawable.shape_air_hazardous
			else -> R.drawable.shape_air_hazardous
		}
	}

	@JvmStatic
	fun itemAQI(aqi: Int): String {
		return when (aqi) {
			in 0..50 -> "$aqi·优"
			in 51..100 -> "$aqi·良"
			in 101..150 -> "$aqi·轻度"
			in 151..200 -> "$aqi·中度"
			in 201..300 -> "$aqi·重度"
			in 301..500 -> "$aqi·严重"
			else -> "$aqi·严重"
		}
	}
}

/**
 * 北京颜色图片的适配
 */
@BindingAdapter("android:background")
fun adapterBackground(tv: TextView, id: Int) {
	tv.background = ContextCompat.getDrawable(tv.context, id)
}

/**
 *适配ImageView，使得可以加载url的图片，icon true就是圆形头像，否则不处理
 */
@BindingAdapter("srcCompat", "icon", requireAll = false)
fun bindUrl(iv: ImageView, url: String?, icon: Boolean = false) {
	val uri = if (url.isNullOrEmpty() || !url.startsWith("http")) R.drawable.grass else url
	val options =
		if (icon) RequestOptions.circleCropTransform() else RequestOptions.centerCropTransform()
	Glide.with(iv)
		.applyDefaultRequestOptions(options)
		.load(uri)
		.into(iv)
}