package in.zhiwei.aqi.entity;

/**
 * simple简单aqi预报的数据bean类
 * Author: gzw48760.
 * Date: 2018/2/10 0010,14:25.
 */

public class SimpleAQIBean {
    private int aqi;//aqi数值,为当日的aqi最大值
    private String weekDay;//星期和日期
    private String range;//温度，或 aqi数值的范围

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
