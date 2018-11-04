package in.zhiwei.aqi.adapter.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.zhiwei.aqi.R;
import in.zhiwei.aqi.utils.Tools;

/**
 * nearby 的viewHolder
 * Author: gzw48760.
 * Date: 2018/2/8 0008,20:38.
 */

public class NearbyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_aqi_item_nearby)
    TextView tvAQI;
    @BindView(R.id.tv_monitor_item_nearby)
    TextView tvMonitor;
    @BindView(R.id.tv_time_range_item_nearby)
    TextView tvTimeRange;
    @BindView(R.id.cl_item_nearby)
    ConstraintLayout clItemView;//itemView

    public NearbyViewHolder(View itemView) {
        super(itemView);
        //bind butterKnife
        ButterKnife.bind(this, itemView);
    }

    /**
     * 设置aqi数值,以及背景颜色
     *
     * @param aqi aqi数值
     */
    public void setAQI(@NonNull String aqi) {
        tvAQI.setText(aqi);
        //判断是否是数字
        boolean isNum = Tools.isMatch("^[0-9]*$", aqi);
        if (isNum) {
            int aqiNum = Integer.parseInt(aqi);
            int aqiLevelColor = Tools.getAQILevelColor(aqiNum);
            tvAQI.setBackgroundColor(tvAQI.getContext().getResources().getColor(aqiLevelColor));
            //设置字体颜色 aqi > 150 颜色白色，否则，黑色
            int color = aqiNum > 150 ? Color.WHITE : Color.BLACK;
            tvAQI.setTextColor(color);
        } else {
            tvAQI.setBackgroundColor(Color.GRAY);
            tvAQI.setTextColor(Color.LTGRAY);
        }
    }

    /**
     * 设置监测站点的名称
     *
     * @param monitorName 监测站点
     */
    public void setMonitorName(@NonNull String monitorName) {
        tvMonitor.setText(monitorName);
    }

    /**
     * 设置监测站时间区间
     *
     * @param timeRange 数据时间区间
     */
    public void setTimeRange(@NonNull String timeRange) {
        tvTimeRange.setText(timeRange);
    }

    /**
     * 设置item的点击
     *
     * @param listener 点击事件
     */
    public void setItemClickListener(View.OnClickListener listener) {
        clItemView.setOnClickListener(listener);
    }
}
