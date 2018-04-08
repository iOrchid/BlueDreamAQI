package in.zhiwei.aqi.global;

/**
 * App配置全局需要的常量
 * Author: gzw48760.
 * Date: 2018/2/26 0026,17:17.
 */

public class GlobalConstants {

    public static final String SP_KEY_DOWNLOAD_ID = "download_app_id";//下载apk的id标记
    public static final String SP_KEY_IS_AGREE_TIPS = "is_agree_tips";//是否同意服务协议
    public static final String SP_KEY_AQI_SERVER_DATA = "aqi_server_data";//从服务器获取的html原始数据string
    public static final String SP_KEY_HAS_SHOWED = "has_showed";//是否已经长按，用于显隐控制

    /**
     * 私有构造函数
     */
    private GlobalConstants() {
    }

    public static final String SP_CURRENT_CITY_NAME = "current_city_name";//当前城市名称，sp的key
    public static final String SP_CURRENT_CITY_ID = "current_city";//当前城市拼音或英文，sharedPreferences保存需要的key
}
