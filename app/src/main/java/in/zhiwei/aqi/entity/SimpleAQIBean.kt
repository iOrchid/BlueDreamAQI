package `in`.zhiwei.aqi.entity

/**
 * simple简单aqi预报的数据bean类
 * Author: gzw48760.
 * Date: 2018/2/10 0010,14:25.
 */

class SimpleAQIBean {
    var aqi: Int = 0//aqi数值,为当日的aqi最大值
    var weekDay: String? = null//星期和日期
    var range: String? = null//温度，或 aqi数值的范围
}
