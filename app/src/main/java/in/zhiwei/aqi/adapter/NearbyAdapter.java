package in.zhiwei.aqi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.zhiwei.aqi.R;
import in.zhiwei.aqi.adapter.holder.NearbyViewHolder;
import in.zhiwei.aqi.entity.AQIModel;
import in.zhiwei.aqi.global.GlobalConstants;
import in.zhiwei.aqi.utils.Tools;

/**
 * 附近监测站点AQI数据adapter
 * Author: gzw48760.
 * Date: 2018/2/8 0008,20:38.
 */

public class NearbyAdapter extends RecyclerView.Adapter<NearbyViewHolder> {
	private List<AQIModel.NearestBean> mNearbyList;//附近监测站点
	private Context mContext;//内部context对象
	private OnNearbyItemClickListener mItemListener;//item点击监听listener

	/**
	 * 附近检测站点的数据适配器，无参数
	 */
	public NearbyAdapter() {
		mNearbyList = new ArrayList<>();
	}

	@Override
	public NearbyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		mContext = parent.getContext();
		View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_rv_nearby_aqi_fragment, parent, false);
		return new NearbyViewHolder(inflate);
	}

	@Override
	public void onBindViewHolder(NearbyViewHolder holder, int position) {
		AQIModel.NearestBean bean = mNearbyList.get(position);
		holder.setAQI(bean.getAqi());
		holder.setMonitorName(bean.getName());
		//todo 此处需要以后处理，数据开始时间，暂时还不知道如何抓取web数据
		holder.setTimeRange(Tools.weekDayTime(mContext, bean.getVtime(), bean.getVtime()));
		//item 的点击，会切换站点数据
		final String station = Tools.parserStation(bean.getCurl());
		holder.setItemClickListener(v -> {
			mItemListener.onNearbyItemClicked(station);
			//保存当前点击的item的名称
			SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, bean.getName());
		});
	}

	@Override
	public int getItemCount() {
		return mNearbyList == null ? 0 : mNearbyList.size();
	}

	/**
	 * 设置附近监测站数据
	 *
	 * @param nearestBeans 监测站点数据list
	 */
	public void setData(@NonNull List<AQIModel.NearestBean> nearestBeans) {
		if (mNearbyList != null) {
			mNearbyList.clear();
			mNearbyList.addAll(nearestBeans);
		}
		notifyDataSetChanged();
	}

	/**
	 * 注册监听
	 *
	 * @param listener 监听接口对象
	 */
	public void setItemListener(@NonNull OnNearbyItemClickListener listener) {
		this.mItemListener = listener;
	}

	/**
	 * 附近站点的item的点击事件 监听接口
	 */
	public interface OnNearbyItemClickListener {
		/**
		 * 点击item的回调函数
		 *
		 * @param station 点击位置对应站点的string
		 */
		void onNearbyItemClicked(@NonNull String station);
	}
}
