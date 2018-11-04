package in.zhiwei.aqi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.zhiwei.aqi.R;
import in.zhiwei.aqi.adapter.holder.SearchCityViewHolder;
import in.zhiwei.aqi.global.GlobalConstants;

/**
 * 用于搜索城市数据list的adapter
 * Author: gzw48760.
 * Date: 2018/4/10 0010,16:26.
 */
public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityViewHolder> {
	private List<String> mList;//搜索匹配的，aqi站点名称列表
	private OnStationItemClickListener mListener;//listener

	/**
	 * 构造函数
	 */
	public SearchCityAdapter() {
		mList = new ArrayList<>();
	}

	@NonNull
	@Override
	public SearchCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_search, parent, false);
		return new SearchCityViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull SearchCityViewHolder holder, int position) {
		//设置名称
		String name = mList.get(position);
		holder.setCityName(name);
		holder.setItemClickListener(v -> {
			mListener.onItemClicked(name);
			//保存点击选择的name
			SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, name);
		});
	}

	@Override
	public int getItemCount() {
		return mList == null ? 0 : mList.size();
	}

	/**
	 * 设置listener
	 *
	 * @param listener listener的对象
	 */
	public void setItemListener(@NonNull OnStationItemClickListener listener) {
		this.mListener = listener;
	}

	/**
	 * 更新数据源
	 *
	 * @param data 搜索匹配的stations
	 */
	public void setData(@NonNull List<String> data) {
		if (!data.isEmpty()) {
			mList.clear();
			mList.addAll(data);
		}
		notifyDataSetChanged();
	}

	/**
	 * 搜索站点匹配的item点击 接口
	 */
	public interface OnStationItemClickListener {
		/**
		 * item点击
		 *
		 * @param name 选择的城市、站点名称
		 */
		void onItemClicked(@NonNull String name);
	}
}
