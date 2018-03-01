package in.zhiwei.aqi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.zhiwei.aqi.R;
import in.zhiwei.aqi.adapter.holder.SimpleViewHolder;
import in.zhiwei.aqi.entity.SimpleAQIBean;

/**
 * 星期aqi的适配器
 * Author: gzw48760.
 * Date: 2018/2/8 0008,12:01.
 */

public class SimpleAQIAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

    private List<SimpleAQIBean> mAQI;//aqi数据list

    /**
     * aqi预报adapter的构造函数
     */
    public SimpleAQIAdapter() {
        mAQI = new ArrayList<>();
    }

    /**
     * 构造函数，接收aqi list
     *
     * @param aqi list数据源
     */
    public SimpleAQIAdapter(@NonNull List<SimpleAQIBean> aqi) {
        mAQI = new ArrayList<>();
        if (!aqi.isEmpty()) {
            mAQI.clear();
            mAQI.addAll(aqi);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_simple_api_fragment, parent, false);
        return new SimpleViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        SimpleAQIBean aqiBean = mAQI.get(position);
        holder.setWeekDay(aqiBean.getWeekDay(), aqiBean.getAqi());
        holder.setTempRange(aqiBean.getRange());
    }

    @Override
    public int getItemCount() {
        return mAQI == null ? 0 : mAQI.size();
    }

    /**
     * 设置数据源list
     *
     * @param aqi aqi数据list
     */
    public void setData(@NonNull List<SimpleAQIBean> aqi) {
        if (!aqi.isEmpty()) {
            mAQI.clear();
            mAQI.addAll(aqi);
        }
        notifyDataSetChanged();
    }
}
