package org.zhiwei.aqi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.zhiwei.aqi.databinding.ItemRvStationMainBinding
import org.zhiwei.aqi.entity.ItemStation

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月26日 17:45
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 首页aqi站点的列表适配器
 */
class StationAdapter :
	RecyclerView.Adapter<StationAdapter.StationVH>() {
	private val mList = mutableListOf<ItemStation>()

	/**
	 * viewHolder
	 */
	class StationVH(private val binding: ItemRvStationMainBinding) :
		RecyclerView.ViewHolder(binding.root) {

		companion object {
			fun create(container: ViewGroup): StationVH {
				val inflate = ItemRvStationMainBinding.inflate(
					LayoutInflater.from(container.context),
					container,
					false
				)
				return StationVH(inflate)
			}
		}

		/**
		 * 绑定item的数据
		 */
		fun bind(bean: ItemStation) {
			binding.info = bean
			binding.executePendingBindings()
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StationVH.create(parent)

	override fun getItemCount() = mList.size

	override fun onBindViewHolder(holder: StationVH, position: Int) {
		holder.bind(mList[position])
	}

	/**
	 * 刷新数据
	 */
	fun updateList(list: List<ItemStation>) {
		mList.clear()
		mList.addAll(list)
		notifyDataSetChanged()
	}
}