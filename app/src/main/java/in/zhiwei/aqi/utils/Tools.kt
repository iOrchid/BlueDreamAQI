package `in`.zhiwei.aqi.utils

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.entity.AQIModel
import `in`.zhiwei.aqi.entity.SimpleAQIBean
import android.content.Context
import android.text.TextUtils
import com.blankj.utilcode.util.AppUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * App 辅助工具类
 * Author: gzw48760.
 * Date: 2018/2/7 0007,20:23.
 */

object Tools {

    private val FORMAT_TYPE_DATE_TIME_COMMON = "yyyy-MM-dd HH:mm:ss"//常用的普通时间格式化格式
    private val FORMAT_TYPE_AQI_FORECAST = "yyyy-MM-dd'T'HH:mm:ss+mm:ss"//AQI forecast的时间格式

    /**
     * 判断设备是否是中文环境
     *
     * @return true 中文，false 非中文环境
     */
    val isChinese: Boolean
        get() {
            val language = Locale.getDefault().language
            return TextUtils.equals("zh", language)
        }

    /**
     * 获取AQI网站可支持的语言，若不支持，默认中文
     *
     * @return 可用的语言i18n代码
     */
    //获取机型设置语言，但网站支持语言种类有限，若不支持的话，默认为中文
    //获取的中文，是zh，而不是cn，
    val supportedLanguage: String
        get() {
            val languages = arrayOf("en", "cn", "jp", "es", "kr", "ru", "hk", "fr", "pl", "de", "pt", "vn")
            var language = Locale.getDefault().language
            if (!Arrays.asList(*languages).contains(language)) {
                language = "cn"
            }
            return language
        }

    /**
     * 获取App的版本号
     *
     * @return versionName
     */
    val appVersion: String
        get() = AppUtils.getAppVersionName()

    /**
     * 根据aqi的指数，获取对应level级别
     *
     * @param aqi aqi指数
     * @return string的level
     */
    fun getAQILevel(context: Context, aqi: Int): String {
        val level: String
        if (aqi < 0) {
            level = context.getString(R.string.str_aqi_should_not_lessthan_zero)
        } else if (aqi >= 0 && aqi <= 50) {
            level = context.getString(R.string.str_aqi_level_good)
        } else if (aqi >= 51 && aqi <= 100) {
            level = context.getString(R.string.str_aqi_level_moderate)
        } else if (aqi >= 101 && aqi <= 150) {
            level = context.getString(R.string.str_aqi_level_light_unhealthy)
        } else if (aqi >= 151 && aqi <= 200) {
            level = context.getString(R.string.str_aqi_level_unhealthy)
        } else if (aqi >= 201 && aqi <= 300) {
            level = context.getString(R.string.str_aqi_level_very_unhealthy)
        } else {
            level = context.getString(R.string.str_aqi_level_hazardous)
        }
        return level
    }

    /**
     * 根据aqi 指数，获取对应的颜色等级
     *
     * @param aqi aqi指数
     * @return 对应颜色的res id
     */
    fun getAQILevelColor(aqi: Int): Int {
        val level: Int
        if (aqi <= 50) {
            level = R.color.colorAQIGood
            //            Color.parseColor("#019865");
        } else if (aqi >= 51 && aqi <= 100) {
            level = R.color.colorAQIModerate
        } else if (aqi >= 101 && aqi <= 150) {
            level = R.color.colorAQIUSG
        } else if (aqi >= 151 && aqi <= 200) {
            level = R.color.colorAQIUnhealthy
        } else if (aqi >= 201 && aqi <= 300) {
            level = R.color.colorAQIVeryUnhealthy
        } else {
            level = R.color.colorAQIHazardous
        }
        return level
    }

    /**
     * 根据aqi 指数，获取对应颜色等级的layer-list的drawable
     *
     * @param aqi aqi指数
     * @return 对应颜色等级的layer-list的drawable res id
     */
    fun getAQILevelDrawable(aqi: Int): Int {
        val level: Int
        if (aqi <= 50) {
            level = R.drawable.layer_cloud_aqi_good
        } else if (aqi >= 51 && aqi <= 100) {
            level = R.drawable.layer_cloud_aqi_moderate
        } else if (aqi >= 101 && aqi <= 150) {
            level = R.drawable.layer_cloud_aqi_usg
        } else if (aqi >= 151 && aqi <= 200) {
            level = R.drawable.layer_cloud_aqi_unhealthy
        } else if (aqi >= 201 && aqi <= 300) {
            level = R.drawable.layer_cloud_aqi_very_unhealthy
        } else {
            level = R.drawable.layer_cloud_aqi_hazardous
        }
        return level
    }

    /**
     * 根据aqi 指数，获取对应颜色等级的layer-list的drawable
     *
     * @param aqi aqi指数
     * @return 对应颜色等级的layer-list的drawable res id
     */
    fun getAQILevelBG(aqi: Int): Int {
        val level: Int
        if (aqi <= 50) {
            level = R.drawable.shape_aqi_good_widget
        } else if (aqi >= 51 && aqi <= 100) {
            level = R.drawable.shape_aqi_moderate_widget
        } else if (aqi >= 101 && aqi <= 150) {
            level = R.drawable.shape_aqi_usg_widget
        } else if (aqi >= 151 && aqi <= 200) {
            level = R.drawable.shape_aqi_unhealthy_widget
        } else if (aqi >= 201 && aqi <= 300) {
            level = R.drawable.shape_aqi_very_unhealthy_widget
        } else {
            level = R.drawable.shape_aqi_hazardous_widget
        }
        return level
    }

    /**
     * 生成js代码，根据指定的div
     *
     * @param divs 需要生成js代码的对应div的数组
     * @return 返回js代码
     */
    fun getJS(divs: Array<String>): String {
        val js = StringBuilder("javascript:")
        for (i in divs.indices) {
            js.append("var adDiv").append(i).append("= document.getElementById('").append(divs[i]).append("');if(adDiv")
                .append(i).append(" != null)adDiv").append(i).append(".parentNode.removeChild(adDiv").append(i)
                .append(");")
            //            js += "var adDiv" + i + "= document.getElementById('" + divs[i] + "');if(adDiv" + i + " != null)adDiv" + i + ".parentNode.removeChild(adDiv" + i + ");";
        }
        return js.toString()
    }

    /**
     * 根据格式，格式化string
     *
     * @param format string的格式
     * @param args   传来的参数
     * @return 格式后的string
     */
    fun strFormat(format: String, vararg args: Any): String {
        return String.format(Locale.CHINESE, format, *args)
    }

    /**
     * 按照 2018-02-08 21:01:36的格式，格式化long时间为string
     *
     * @param time long类型的time
     * @return string的格式化后的时间
     */
    fun timeFormat(time: Long): String {
        var time = time
        //时间值为秒，则 * 1000
        if (java.lang.Long.toString(time).length == 10) {
            time *= 1000
        }
        val format = SimpleDateFormat(FORMAT_TYPE_DATE_TIME_COMMON, Locale.CHINESE)
        return format.format(Date(time))
    }

    /**
     * 将aqi的string形式的时间值 "2018-02-09T12:00:00+00:00" 转化为long的毫秒值
     *
     * @param strDate string 类型的aqi时间值
     * @return long类型的毫秒, 转为北京时区的long，即 0 时区 +8 小时
     */
    private fun strDate2CNLong(strDate: String): Long {
        val format = SimpleDateFormat(FORMAT_TYPE_AQI_FORECAST, Locale.CHINESE)
        var date = Date()
        try {
            date = format.parse(strDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        //北京时间，在0 时区时间上，+ 8 小时
        return date.time + 8 * 60 * 60 * 1000
    }

    /**
     * 获取aqi预报的日期格式
     *
     * @param time long类型的time
     * @return string格式化的日期，如 星期六 10，其中10 标识日期
     */
    private fun weekDayFormat(context: Context, time: Long): String {
        val format = SimpleDateFormat("d", Locale.CHINESE)
        val day = format.format(time)
        val weekDay = getWeekDay(context, time)
        return strFormat("%s %s", weekDay, day)
    }

    /**
     * 将从html解析的aqibean数据对象，转化为simple中需要的aqibean
     *
     * @param beans 原始aqibean
     * @return 简单的aqibean
     */
    fun getSimpleAQiList(context: Context, beans: List<AQIModel.ForecastBean.AqiBean>): List<SimpleAQIBean> {
        val list = ArrayList<SimpleAQIBean>()
        //获取所有，星期，日期的set集合,linked HashSet,链表结构，可以维持插入顺序
        val weekDaySet = LinkedHashSet<String>()
        var weekDay: String
        for (bean in beans) {
            //aqibean 的时间，string转化为long，再 +8小时，为北京时间的long毫秒
            val cnLong = strDate2CNLong(bean.t!!)
            weekDay = weekDayFormat(context, cnLong)
            weekDaySet.add(weekDay)
        }
        //获取同一日期下，所有aqi数据的汇集的set集合
        var aqiSet: MutableSet<Int>
        var simpleAQIBean: SimpleAQIBean
        for (day in weekDaySet) {
            simpleAQIBean = SimpleAQIBean()
            aqiSet = HashSet()
            simpleAQIBean.weekDay = day
            for (bean in beans) {
                val cnLong = strDate2CNLong(bean.t!!)
                weekDay = weekDayFormat(context, cnLong)
                if (TextUtils.equals(day, weekDay)) {
                    aqiSet.addAll(bean.v!!)
                }
            }
            val min = Collections.min(aqiSet)
            val max = Collections.max(aqiSet)
            simpleAQIBean.range = strFormat("%d - %d", min, max)
            simpleAQIBean.aqi = max
            list.add(simpleAQIBean)
        }
        return list
    }

    /**
     * @param context   context
     * @param firstTime 数据开始时间
     * @param nowTime   long类型的时间戳
     * @return 返回 “星期五 8-18:00”这样格式的string
     */
    fun weekDayTime(context: Context, firstTime: Long, nowTime: Long): String {
        var firstTime = firstTime
        var nowTime = nowTime
        //时间值为秒，则 * 1000 转化为毫秒
        if (java.lang.Long.toString(nowTime).length == 10) {
            nowTime *= 1000
        }
        if (java.lang.Long.toString(firstTime).length == 10) {
            firstTime *= 1000
        }
        val startFormat = SimpleDateFormat("H", Locale.CHINESE)
        val endFormat = SimpleDateFormat("HH:mm", Locale.CHINESE)
        val startTime = startFormat.format(firstTime)
        val endTime = endFormat.format(nowTime)
        //添加星期几
        val weekDay = getWeekDay(context, nowTime)
        return String.format(Locale.CHINESE, "(%s %s - %s)", weekDay, startTime, endTime)
    }

    /**
     * 根据星期中的天数获取对应星期
     *
     * @param context context
     * @param time    long类型的时间，毫秒
     * @return 中文的星期几
     */
    private fun getWeekDay(context: Context, time: Long): String {
        var time = time
        if (java.lang.Long.toString(time).length == 10) {
            time *= 1000
        }
        val instance = Calendar.getInstance()
        instance.timeInMillis = time
        val day = instance.get(Calendar.DAY_OF_WEEK)
        val weekDay: String
        when (day) {
            Calendar.SUNDAY -> weekDay = context.getString(R.string.str_sunday)
            Calendar.MONDAY -> weekDay = context.getString(R.string.str_monday)
            Calendar.TUESDAY -> weekDay = context.getString(R.string.str_tuesday)
            Calendar.WEDNESDAY -> weekDay = context.getString(R.string.str_wednesday)
            Calendar.THURSDAY -> weekDay = context.getString(R.string.str_thursday)
            Calendar.FRIDAY -> weekDay = context.getString(R.string.str_friday)
            Calendar.SATURDAY -> weekDay = context.getString(R.string.str_saturday)
            else -> weekDay = context.getString(R.string.str_sunday)
        }
        return weekDay
    }

    /**
     * 正则匹配
     *
     * @param regex 匹配正则表达式
     * @param text  待匹配string
     * @return true 匹配
     */
    fun isMatch(regex: String, text: String): Boolean {
        val pattern = Pattern.compile(regex)
        // 忽略大小写的写法
        //        Pattern pat = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        val matcher = pattern.matcher(text)
        // 字符串是否与正则表达式相匹配
        return matcher.matches()
    }

    /**
     * 根据指定的正则，返回匹配到的内容
     *
     * @param regex 正则格式
     * @param text  待匹配内容
     * @return 返回所有匹配的数据list
     */
    fun getMatched(regex: String, text: String): List<String> {
        val matches = ArrayList<String>()
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(text)
        while (matcher.find()) {
            matches.add(matcher.group())
        }
        return matches
    }

    /**
     * 从站点curl中解析出需要的字段
     * 因为AQI网站的language都是两位数的cn，uk，us之类的格式所以，可不用正则那么麻烦"(\\/city\\/)[a-zA-Z\\.0-9\\/]+(\\/cn)"
     *
     * @param url http://aqicn.org/city/beijing/yizhuangkaifaqu/cn/m/ 这个样式的url
     * @return 返回出city/ 。。。/cn之间的字符串
     */
    fun parserStation(url: String): String {
        return url.substring(22, url.length - 6)
    }

    /**
     * 请求网络错误信息的合理化展示
     *
     * @param error 原始错误信息
     * @return 人性化的提示信息
     */
    fun convertError(error: String): String {
        val tips: String
        when (error) {
            "SSL handshake timed out"//ssl握手超时
                , "connect timed out"//链接超时
                , "failed to connect to aqicn.org/106.186.25.131 (port 443) after 10000ms"//链接服务器超时
            -> tips = "服务器连接超时"
            "HTTP 404 Not Found"//网络请求错误
            -> tips = "服务器404错误"
            "Unable to resolve host \"api.waqi.info\": No address associated with hostname", "Unable to resolve host \"aqicn.org\": No address associated with hostname", "Unable to resolve host \"raw.githubusercontent.com\": No address associated with hostname" -> tips =
                    "网络错误,请检查网络连接是否正常"
            "Invalid index 0, size is 0"//搜索城市、站点的错误提示
            -> tips = "未查询到匹配城市或站点，请检查输入"
            else -> tips = error
        }
        return tips
    }

    /**
     * 有时候会出现中文的更新数据为空，所以使用英文的来转化一下
     *
     * @param str 英文的更新时间描述
     * @return 中文描述
     */
    fun translateEn(str: String): String {
        //Updated on Sunday 11:00
        val weekday = str.substring(11, str.length - 5).trim { it <= ' ' }
        val updateTime = StringBuilder("更新时间: ")
        when (weekday) {
            "Sunday" -> updateTime.append("星期日 ")
            "Monday" -> updateTime.append("星期一 ")
            "Tuesday" -> updateTime.append("星期二 ")
            "Wednesday" -> updateTime.append("星期三 ")
            "Thursday" -> updateTime.append("星期四 ")
            "Friday" -> updateTime.append("星期五 ")
            "Saturday" -> updateTime.append("星期六 ")
            else -> {
            }
        }
        val time = str.substring(str.length - 5)
        return updateTime.append(time).toString()
    }
}
/**
 * 私有化构造函数
 */

