package in.zhiwei.aqi.entity;

/**
 * AQI的数据封装体
 * Author: gzw48760.
 * Date: 2018/2/9 0009,17:28.
 */

public class AQIEntity {
    private AQIModel aqiModel;//getAqiModel的html数据对应数据
    private OtherAQIData otherAQIData;//其它零散的数据

    public AQIModel getAqiModel() {
        return aqiModel;
    }

    public void setAqiModel(AQIModel aqiModel) {
        this.aqiModel = aqiModel;
    }

    public OtherAQIData getOtherAQIData() {
        return otherAQIData;
    }

    public void setOtherAQIData(OtherAQIData otherAQIData) {
        this.otherAQIData = otherAQIData;
    }
}
