package `in`.zhiwei.aqi.adapter.holder

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.utils.Tools
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife

/**
 * nearby 的viewHolder
 * Author: gzw48760.
 * Date: 2018/2/8 0008,20:38.
 */

class NearbyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.tv_aqi_item_nearby)
    internal lateinit var tvAQI: TextView
    @BindView(R.id.tv_monitor_item_nearby)
    internal lateinit var tvMonitor: TextView
    @BindView(R.id.tv_time_range_item_nearby)
    internal lateinit var tvTimeRange: TextView
    @BindView(R.id.cl_item_nearby)
    internal lateinit var clItemView: ConstraintLayout//itemView

    init {
        //bind butterKnife
        ButterKnife.bind(this, itemView)
    }

    /**
     * 设置aqi数值,以及背景颜色
     *
     * @param aqi aqi数值
     */
    fun setAQI(aqi: String) {
        tvAQI.text = aqi
        //判断是否是数字
        val isNum = Tools.isMatch("^[0-9]*$", aqi)
        if (isNum) {
            val aqiNum = Integer.parseInt(aqi)
            val aqiLevelColor = Tools.getAQILevelColor(aqiNum)
            tvAQI.setBackgroundColor(tvAQI.context.resources.getColor(aqiLevelColor))
            //设置字体颜色 aqi > 150 颜色白色，否则，黑色
            val color = if (aqiNum > 150) Color.WHITE else Color.BLACK
            tvAQI.setTextColor(color)
        } else {
            tvAQI.setBackgroundColor(Color.GRAY)
            tvAQI.setTextColor(Color.LTGRAY)
        }
    }

    /**
     * 设置监测站点的名称
     *
     * @param monitorName 监测站点
     */
    fun setMonitorName(monitorName: String) {
        tvMonitor.text = monitorName
    }

    /**
     * 设置监测站时间区间
     *
     * @param timeRange 数据时间区间
     */
    fun setTimeRange(timeRange: String) {
        tvTimeRange.text = timeRange
    }

    /**
     * 设置item的点击
     *
     * @param listener 点击事件
     */
    fun setItemClickListener(listener: (Any) -> Unit) {
        clItemView.setOnClickListener(listener)
    }
}
