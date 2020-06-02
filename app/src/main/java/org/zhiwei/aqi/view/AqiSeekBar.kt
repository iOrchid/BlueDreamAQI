package org.zhiwei.aqi.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StyleRes
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import org.zhiwei.aqi.R
import org.zhiwei.libcore.LogKt


/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年05月21日 17:12
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 用于显示aqi当前状态位置的自定义控件
 */
class AqiSeekBar : View {

	constructor(context: Context) : super(context) {
		initConfig(context, null, 0, 0)
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

		initConfig(context, attrs, 0, 0)
	}

	constructor(context: Context, attrs: AttributeSet?, defaultAttr: Int) : super(
		context,
		attrs,
		defaultAttr
	) {
		initConfig(context, attrs, defaultAttr, 0)
	}

	constructor(
		context: Context,
		attrs: AttributeSet?,
		defaultAttr: Int,
		@StyleRes defStyleRes: Int
	) : super(
		context,
		attrs,
		defaultAttr,
		defStyleRes
	) {
		initConfig(context, attrs, defaultAttr, defStyleRes)
	}

	//region 成员属性

	private val bgPaint = Paint()
	private val buoyPaint = Paint()
	private val tvPaint = Paint()

	private val barHeight = 15f//seekbar的背景条的高度

	private val aqiColorLevels = intArrayOf(
		Color.parseColor("#009966"),
		Color.parseColor("#FFDE33"),
		Color.parseColor("#FF9933"),
		Color.parseColor("#CC0033"),
		Color.parseColor("#660099"),
		Color.parseColor("#7E0023")
	)
	private val aqiColorPos = floatArrayOf(0.166f, 0.33f, 0.5f, 0.66f, 0.83f, 1f)

	private var aqiLevelText = AQI_LEVEL_TEXT_GOOD
	private var aqiTvWidth: Float


	private var aqiTvPos: Float = 0f//浮标的文字的位置
	//endregion

	private fun initConfig(
		context: Context,
		attrs: AttributeSet?,
		defaultAttr: Int,
		defStyleRes: Int
	) {
		val typedArray = context.theme.obtainStyledAttributes(
			attrs,
			R.styleable.AqiSeekBar,
			defaultAttr,
			defStyleRes
		)

	}


	init {
		//背景画笔
		bgPaint.color = Color.BLUE
		bgPaint.style = Paint.Style.FILL
		bgPaint.strokeWidth = 2f
		bgPaint.isAntiAlias = true
		//文字画笔
		tvPaint.color = Color.WHITE
		tvPaint.textSize = 36f
		tvPaint.strokeWidth = 2f
		tvPaint.style = Paint.Style.FILL

		aqiTvWidth = tvPaint.measureText(aqiLevelText)

		aqiTvPos = (width - aqiTvWidth) / 2

		//浮标画笔
		buoyPaint.color = aqiColorLevels[0]
		buoyPaint.style = Paint.Style.FILL
		buoyPaint.isAntiAlias = true
	}

	//region 函数api


	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)

		val widthSize = MeasureSpec.getSize(widthMeasureSpec)
		val widthMode = MeasureSpec.getMode(widthMeasureSpec)

		val heightSize = MeasureSpec.getSize(heightMeasureSpec)
		val heightMode = MeasureSpec.getMode(heightMeasureSpec)

		//实际宽高像素

		LogKt.w("onMeasure 110行: width spec $widthMeasureSpec $widthMode $widthSize // height spec $heightMeasureSpec $heightMode $heightSize margin $marginStart $marginEnd  padding $paddingStart $paddingEnd")
	}

	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
		super.onSizeChanged(w, h, oldw, oldh)
		LogKt.i("onSizeChanged 115行: $w $oldw // $h  $oldh  margin $marginStart $marginEnd  padding $paddingStart $paddingEnd")
	}


	override fun onDraw(canvas: Canvas?) {
		super.onDraw(canvas)
		canvas ?: return
		//绘制背景,注意得到with，height padding等都是像素值
		val linearGradient =
			LinearGradient(0f, 0f, width.toFloat(), 0f, aqiColorLevels, null, Shader.TileMode.CLAMP)
		bgPaint.shader = linearGradient
		canvas.drawRoundRect(
			paddingStart.toFloat(),
			(height - barHeight) / 2f,
			width.toFloat() - paddingEnd.toFloat(),
			(height + barHeight) / 2f,
			barHeight / 2f,
			barHeight / 2f,
			bgPaint
		)

		canvas.drawCircle(width / 2f, height / 2f, 30f, buoyPaint)
		//绘制文字，需要计算文字大小，来确定baseline，才能准确的绘制位置
		val fontMetrics = tvPaint.fontMetrics
		val h = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
		canvas.drawText(aqiLevelText, (width - aqiTvWidth) / 2f, (height + h) / 2f, tvPaint)

		LogKt.d("onDraw 100行: h:$height w:$width marginStart:$marginStart marginEnd $marginEnd padding $paddingStart ")
	}

	override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
		super.onLayout(changed, left, top, right, bottom)

		LogKt.e("onLayout 139行: $changed $left $top $right $bottom")
	}

	//endregion

	fun setAqiNum(aqi: Int) {


		invalidate()
	}


	/**
	 * 根据aqi的数值，以及所属等级，计算出对应的颜色值
	 */
	private fun genAqiColor(aqi: Int) {
		when (aqi) {
			in 0..50 -> AQI_LEVEL_TEXT_GOOD
			in 51..100 -> AQI_LEVEL_TEXT_MODERATE
			in 101..150 -> AQI_LEVEL_TEXT_UNHEALTHY_FOR_SENSITIVE_GROUP
			in 151..200 -> AQI_LEVEL_TEXT_UNHEALTHY
			in 200..300 -> AQI_LEVEL_TEXT_VERY_UNHEALTHY
			in 300..500 -> AQI_LEVEL_TEXT_HAZARDOUS
			in 500..Int.MAX_VALUE -> AQI_LEVEL_TEXT_VERY_HAZARDOUS
			else -> AQI_LEVEL_TEXT_UNKNOWN
		}
	}

	/**
	 * 根据aqi数值，得到对应等级的描述文字
	 */
	private fun getAqiText(aqi: Int): String {
		return when (aqi) {
			in 0..50 -> AQI_LEVEL_TEXT_GOOD
			in 51..100 -> AQI_LEVEL_TEXT_MODERATE
			in 101..150 -> AQI_LEVEL_TEXT_UNHEALTHY_FOR_SENSITIVE_GROUP
			in 151..200 -> AQI_LEVEL_TEXT_UNHEALTHY
			in 200..300 -> AQI_LEVEL_TEXT_VERY_UNHEALTHY
			in 300..500 -> AQI_LEVEL_TEXT_HAZARDOUS
			in 500..Int.MAX_VALUE -> AQI_LEVEL_TEXT_VERY_HAZARDOUS
			else -> AQI_LEVEL_TEXT_UNKNOWN
		}
	}


	companion object {
		private const val AQI_LEVEL_TEXT_GOOD = "优"
		private const val AQI_LEVEL_TEXT_MODERATE = "良"
		private const val AQI_LEVEL_TEXT_UNHEALTHY_FOR_SENSITIVE_GROUP = "差"
		private const val AQI_LEVEL_TEXT_UNHEALTHY = "劣"
		private const val AQI_LEVEL_TEXT_VERY_UNHEALTHY = "糟"
		private const val AQI_LEVEL_TEXT_HAZARDOUS = "危"
		private const val AQI_LEVEL_TEXT_VERY_HAZARDOUS = "禁"
		private const val AQI_LEVEL_TEXT_UNKNOWN = "未"
	}

}