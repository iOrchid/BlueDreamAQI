package org.zhiwei.aqi.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
		bgPaint.strokeWidth = 20f
		bgPaint.isAntiAlias = true
		//浮标画笔
		buoyPaint.color = Color.RED
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
		canvas.drawRoundRect(
			paddingStart.toFloat(),
			0f,
			width.toFloat() - paddingEnd.toFloat(),
			height.toFloat(),
			10f,
			10f,
			bgPaint
		)
		canvas.drawCircle(width / 2f, height / 2f, 50f, buoyPaint)
		LogKt.d("onDraw 100行: h:$height w:$width marginStart:$marginStart marginEnd $marginEnd padding $paddingStart ")
	}

	override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
		super.onLayout(changed, left, top, right, bottom)

		LogKt.e("onLayout 139行: $changed $left $top $right $bottom")
	}

	//endregion


}