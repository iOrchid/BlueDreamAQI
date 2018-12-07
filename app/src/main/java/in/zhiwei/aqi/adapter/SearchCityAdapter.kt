package `in`.zhiwei.aqi.adapter

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.adapter.holder.SearchCityViewHolder
import `in`.zhiwei.aqi.global.GlobalConstants
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SPUtils
import java.util.*

/**
 * 用于搜索城市数据list的adapter
 * Author: gzw48760.
 * Date: 2018/4/10 0010,16:26.
 */
class SearchCityAdapter : RecyclerView.Adapter<SearchCityViewHolder>() {
    private val mList: MutableList<String>?//搜索匹配的，aqi站点名称列表
    private var mListener: OnStationItemClickListener? = null//listener

    /**
     * 构造函数
     */
    init {
        mList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_search, parent, false)
        return SearchCityViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchCityViewHolder, position: Int) {
        //设置名称
        val name = mList!![position]
        holder.setCityName(name)
        holder.setItemClickListener {
            mListener!!.onItemClicked(name)
            //保存点击选择的name
            SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, name)
        }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    /**
     * 设置listener
     *
     * @param listener listener的对象
     */
    fun setItemListener(listener: OnStationItemClickListener) {
        this.mListener = listener
    }

    /**
     * 更新数据源
     *
     * @param data 搜索匹配的stations
     */
    fun setData(data: List<String>) {
        if (!data.isEmpty()) {
            mList!!.clear()
            mList.addAll(data)
        }
        notifyDataSetChanged()
    }

    /**
     * 搜索站点匹配的item点击 接口
     */
    interface OnStationItemClickListener {
        /**
         * item点击
         *
         * @param name 选择的城市、站点名称
         */
        fun onItemClicked(name: String)
    }
}
