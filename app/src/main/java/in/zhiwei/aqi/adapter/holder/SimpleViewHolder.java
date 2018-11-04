package in.zhiwei.aqi.adapter.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.zhiwei.aqi.R;
import in.zhiwei.aqi.utils.Tools;

/**
 * Author: gzw48760.
 * Date: 2018/2/8 0008,12:02.
 */

public class SimpleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_weekday_item_api_simple)
    TextView tvWeekDay;//空气质量预报item的星期，日期
    @BindView(R.id.tv_temperature_range_item_api_simple)
    TextView tvTemperatureRange;//item的温度范围

    /**
     * 构造函数
     *
     * @param itemView item的布局view
     */
    public SimpleViewHolder(View itemView) {
        super(itemView);
        //adapter 中使用butterKnife
        ButterKnife.bind(this, itemView);
    }

    /**
     * 设置星期 日期,以及对应aqi的背景色
     *
     * @param weekDay 星期
     * @param aqi     当日的aqi最大值，用于设置背景色
     */
    public void setWeekDay(@NonNull String weekDay, int aqi) {
        tvWeekDay.setText(weekDay);
        int colorResId = Tools.getAQILevelDrawable(aqi);
        //字体颜色，用于与背景色区分
        int textColor = aqi < 150 ? Color.BLACK : Color.WHITE;
        tvWeekDay.setTextColor(textColor);
        tvWeekDay.setBackgroundResource(colorResId);
    }

    /**
     * 设置，温度范围、
     * 暂时一期版本，设置为aqi的最小，最大值
     *
     * @param range 范围值
     */
    public void setTempRange(@NonNull String range) {
        tvTemperatureRange.setText(range);
    }
}
