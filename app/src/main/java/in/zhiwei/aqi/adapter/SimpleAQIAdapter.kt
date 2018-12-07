package `in`.zhiwei.aqi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.adapter.holder.SimpleViewHolder
import `in`.zhiwei.aqi.entity.SimpleAQIBean

/**
 * 星期aqi的适配器
 * Author: gzw48760.
 * Date: 2018/2/8 0008,12:01.
 */

class SimpleAQIAdapter : RecyclerView.Adapter<SimpleViewHolder> {

    private var mAQI: MutableList<SimpleAQIBean>? = null//aqi数据list

    /**
     * aqi预报adapter的构造函数
     */
    constructor() {
        mAQI = ArrayList()
    }

    /**
     * 构造函数，接收aqi list
     *
     * @param aqi list数据源
     */
    constructor(aqi: List<SimpleAQIBean>) {
        mAQI = ArrayList()
        if (!aqi.isEmpty()) {
            mAQI!!.clear()
            mAQI!!.addAll(aqi)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_simple_api_fragment, parent, false)
        return SimpleViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val aqiBean = mAQI!![position]
        holder.setWeekDay(aqiBean.weekDay!!, aqiBean.aqi)
        holder.setTempRange(aqiBean.range!!)
    }

    override fun getItemCount(): Int {
        return if (mAQI == null) 0 else mAQI!!.size
    }

    /**
     * 设置数据源list
     *
     * @param aqi aqi数据list
     */
    fun setData(aqi: List<SimpleAQIBean>) {
        if (!aqi.isEmpty()) {
            mAQI!!.clear()
            mAQI!!.addAll(aqi)
        }
        notifyDataSetChanged()
    }
}
