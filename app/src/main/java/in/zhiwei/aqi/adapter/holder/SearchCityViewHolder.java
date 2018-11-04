package in.zhiwei.aqi.adapter.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.zhiwei.aqi.R;

/**
 * 城市、站点list的viewHolder
 * Author: gzw48760.
 * Date: 2018/4/10 0010,16:27.
 */
public class SearchCityViewHolder extends RecyclerView.ViewHolder {
	@BindView(R.id.cl_item_city_search)
	ConstraintLayout clItem;
	@BindView(R.id.tv_name_item_city_search)
	TextView tvName;

	/**
	 * 构造函数
	 *
	 * @param itemView root view
	 */
	public SearchCityViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	/**
	 * 设置item的name
	 *
	 * @param name 匹配的站点名称
	 */
	public void setCityName(@NonNull String name) {
		tvName.setText(name);
	}

	/**
	 * 设置item的点击
	 *
	 * @param listener 点击事件
	 */
	public void setItemClickListener(View.OnClickListener listener) {
		clItem.setOnClickListener(listener);
	}
}
