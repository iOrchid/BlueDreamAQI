package `in`.zhiwei.aqi.adapter.holder

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.utils.Tools
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife

/**
 * Author: gzw48760.
 * Date: 2018/2/8 0008,12:02.
 */

class SimpleViewHolder
/**
 * 构造函数
 *
 * @param itemView item的布局view
 */
    (itemView: View) : RecyclerView.ViewHolder(itemView) {
    @BindView(R.id.tv_weekday_item_api_simple)
    internal lateinit var tvWeekDay: TextView//空气质量预报item的星期，日期
    @BindView(R.id.tv_temperature_range_item_api_simple)
    internal lateinit var tvTemperatureRange: TextView//item的温度范围

    init {
        //adapter 中使用butterKnife
        ButterKnife.bind(this, itemView)
    }

    /**
     * 设置星期 日期,以及对应aqi的背景色
     *
     * @param weekDay 星期
     * @param aqi     当日的aqi最大值，用于设置背景色
     */
    fun setWeekDay(weekDay: String, aqi: Int) {
        tvWeekDay.text = weekDay
        val colorResId = Tools.getAQILevelDrawable(aqi)
        //字体颜色，用于与背景色区分
        val textColor = if (aqi < 150) Color.BLACK else Color.WHITE
        tvWeekDay.setTextColor(textColor)
        tvWeekDay.setBackgroundResource(colorResId)
    }

    /**
     * 设置，温度范围、
     * 暂时一期版本，设置为aqi的最小，最大值
     *
     * @param range 范围值
     */
    fun setTempRange(range: String) {
        tvTemperatureRange.text = range
    }
}
