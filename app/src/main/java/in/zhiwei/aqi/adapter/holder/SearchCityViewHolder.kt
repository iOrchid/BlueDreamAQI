package `in`.zhiwei.aqi.adapter.holder

import `in`.zhiwei.aqi.R
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife

/**
 * 城市、站点list的viewHolder
 * Author: gzw48760.
 * Date: 2018/4/10 0010,16:27.
 */
class SearchCityViewHolder
/**
 * 构造函数
 *
 * @param itemView root view
 */
    (itemView: View) : RecyclerView.ViewHolder(itemView) {
    @BindView(R.id.cl_item_city_search)
    internal lateinit var clItem: ConstraintLayout
    @BindView(R.id.tv_name_item_city_search)
    internal lateinit var tvName: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

    /**
     * 设置item的name
     *
     * @param name 匹配的站点名称
     */
    fun setCityName(name: String) {
        tvName.text = name
    }

    /**
     * 设置item的点击
     *
     * @param listener 点击事件
     */
    fun setItemClickListener(listener: (Any) -> Unit) {
        clItem.setOnClickListener(listener)
    }
}
