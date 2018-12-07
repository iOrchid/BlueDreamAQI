package `in`.zhiwei.aqi.adapter

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.adapter.holder.NearbyViewHolder
import `in`.zhiwei.aqi.entity.AQIModel
import `in`.zhiwei.aqi.global.GlobalConstants
import `in`.zhiwei.aqi.utils.Tools
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SPUtils
import java.util.*

/**
 * 附近监测站点AQI数据adapter
 * Author: gzw48760.
 * Date: 2018/2/8 0008,20:38.
 */

class NearbyAdapter : RecyclerView.Adapter<NearbyViewHolder>() {
    private val mNearbyList: MutableList<AQIModel.NearestBean>?//附近监测站点
    private var mContext: Context? = null//内部context对象
    private var mItemListener: OnNearbyItemClickListener? = null//item点击监听listener

    /**
     * 附近检测站点的数据适配器，无参数
     */
    init {
        mNearbyList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyViewHolder {
        mContext = parent.context
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.item_rv_nearby_aqi_fragment, parent, false)
        return NearbyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: NearbyViewHolder, position: Int) {
        val bean = mNearbyList!![position]
        holder.setAQI(bean.aqi!!)
        holder.setMonitorName(bean.name!!)
        //todo 此处需要以后处理，数据开始时间，暂时还不知道如何抓取web数据
        holder.setTimeRange(Tools.weekDayTime(mContext!!, bean.vtime, bean.vtime))
        //item 的点击，会切换站点数据
        val station = Tools.parserStation(bean.curl!!)
        holder.setItemClickListener { v ->
            mItemListener!!.onNearbyItemClicked(station)
            //保存当前点击的item的名称
            SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, bean.name)
        }
    }

    override fun getItemCount(): Int {
        return mNearbyList?.size ?: 0
    }

    /**
     * 设置附近监测站数据
     *
     * @param nearestBeans 监测站点数据list
     */
    fun setData(nearestBeans: List<AQIModel.NearestBean>) {
        if (mNearbyList != null) {
            mNearbyList.clear()
            mNearbyList.addAll(nearestBeans)
        }
        notifyDataSetChanged()
    }

    /**
     * 注册监听
     *
     * @param listener 监听接口对象
     */
    fun setItemListener(listener: OnNearbyItemClickListener) {
        this.mItemListener = listener
    }

    /**
     * 附近站点的item的点击事件 监听接口
     */
    interface OnNearbyItemClickListener {
        /**
         * 点击item的回调函数
         *
         * @param station 点击位置对应站点的string
         */
        fun onNearbyItemClicked(station: String)
    }
}
