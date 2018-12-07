package `in`.zhiwei.aqi.network

import `in`.zhiwei.aqi.network.res.NearestRes
import `in`.zhiwei.aqi.network.res.SearchRes
import `in`.zhiwei.aqi.network.res.UpgradeRes
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 获取mapidroid.aqicn.org的aqi服务数据
 * retrofit接口
 * Author: gzw48760.
 * Date: 2018/2/2 0002,18:22.
 */

interface AQIService {
    /**
     * 根据城市名称，语言，获取aqi的web数据html
     * https://aqicn.org
     *
     * @param cityName 城市名称拼音或者英文
     * @param language 语言标记，cn en等
     * @return http请求的返回数据html的string
     */
    @GET("/city/{cityName}/{language}/m/")
    fun getAQIServerHtmlStr(@Path("cityName") cityName: String, @Path("language") language: String): Observable<String>

    /**
     * 根据ip获取附近的aqi站点城市
     *
     * @param from 从哪个城市搜索的附近站点，from为integer 数据 1489代表苏州??
     * @return 返回附近aqi站点数据
     */
    @GET("https://api.waqi.info/mapq/nearest")
    fun getNearestStation(@Query("from") from: String?): Observable<NearestRes>

    /**
     * 根据输入的字符，搜索符合的aqi站点名称
     *
     * @param name 待匹配字符
     * @return 返回匹配的aqi站点list
     */
    @GET("https://wind.waqi.info/nsearch/station/{name}")
    fun searchStation(@Path("name") name: String): Observable<SearchRes>

    /**
     * 检查升级
     * @return string数据
     */
    @GET("https://raw.githubusercontent.com/zhiwei1990/BlueDreamAQI/master/release/update.json")
    fun checkUpgrade(): Observable<UpgradeRes>

}
