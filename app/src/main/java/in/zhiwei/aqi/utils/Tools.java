package in.zhiwei.aqi.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.zhiwei.aqi.R;
import in.zhiwei.aqi.entity.AQIModel;
import in.zhiwei.aqi.entity.SimpleAQIBean;

/**
 * App 辅助工具类
 * Author: gzw48760.
 * Date: 2018/2/7 0007,20:23.
 */

public class Tools {

	private static final String FORMAT_TYPE_DATE_TIME_COMMON = "yyyy-MM-dd HH:mm:ss";//常用的普通时间格式化格式
	private static final String FORMAT_TYPE_AQI_FORECAST = "yyyy-MM-dd'T'HH:mm:ss+mm:ss";//AQI forecast的时间格式

	/**
	 * 私有化构造函数
	 */
	private Tools() {
	}

	/**
	 * 根据aqi的指数，获取对应level级别
	 *
	 * @param aqi aqi指数
	 * @return string的level
	 */
	public static String getAQILevel(@NonNull Context context, int aqi) {
		String level;
		if (aqi < 0) {
			level = context.getString(R.string.str_aqi_should_not_lessthan_zero);
		} else if (aqi >= 0 && aqi <= 50) {
			level = context.getString(R.string.str_aqi_level_good);
		} else if (aqi >= 51 && aqi <= 100) {
			level = context.getString(R.string.str_aqi_level_moderate);
		} else if (aqi >= 101 && aqi <= 150) {
			level = context.getString(R.string.str_aqi_level_light_unhealthy);
		} else if (aqi >= 151 && aqi <= 200) {
			level = context.getString(R.string.str_aqi_level_unhealthy);
		} else if (aqi >= 201 && aqi <= 300) {
			level = context.getString(R.string.str_aqi_level_very_unhealthy);
		} else {
			level = context.getString(R.string.str_aqi_level_hazardous);
		}
		return level;
	}

	/**
	 * 根据aqi 指数，获取对应的颜色等级
	 *
	 * @param aqi aqi指数
	 * @return 对应颜色的res id
	 */
	public static int getAQILevelColor(int aqi) {
		int level;
		if (aqi <= 50) {
			level = R.color.colorAQIGood;
//            Color.parseColor("#019865");
		} else if (aqi >= 51 && aqi <= 100) {
			level = R.color.colorAQIModerate;
		} else if (aqi >= 101 && aqi <= 150) {
			level = R.color.colorAQIUSG;
		} else if (aqi >= 151 && aqi <= 200) {
			level = R.color.colorAQIUnhealthy;
		} else if (aqi >= 201 && aqi <= 300) {
			level = R.color.colorAQIVeryUnhealthy;
		} else {
			level = R.color.colorAQIHazardous;
		}
		return level;
	}

	/**
	 * 根据aqi 指数，获取对应颜色等级的layer-list的drawable
	 *
	 * @param aqi aqi指数
	 * @return 对应颜色等级的layer-list的drawable res id
	 */
	public static int getAQILevelDrawable(int aqi) {
		int level;
		if (aqi <= 50) {
			level = R.drawable.layer_cloud_aqi_good;
		} else if (aqi >= 51 && aqi <= 100) {
			level = R.drawable.layer_cloud_aqi_moderate;
		} else if (aqi >= 101 && aqi <= 150) {
			level = R.drawable.layer_cloud_aqi_usg;
		} else if (aqi >= 151 && aqi <= 200) {
			level = R.drawable.layer_cloud_aqi_unhealthy;
		} else if (aqi >= 201 && aqi <= 300) {
			level = R.drawable.layer_cloud_aqi_very_unhealthy;
		} else {
			level = R.drawable.layer_cloud_aqi_hazardous;
		}
		return level;
	}

	/**
	 * 生成js代码，根据指定的div
	 *
	 * @param divs 需要生成js代码的对应div的数组
	 * @return 返回js代码
	 */
	public static String getJS(@NonNull String[] divs) {
		StringBuilder js = new StringBuilder("javascript:");
		for (int i = 0; i < divs.length; i++) {
			js.append("var adDiv").append(i).append("= document.getElementById('").append(divs[i]).append("');if(adDiv").append(i).append(" != null)adDiv").append(i).append(".parentNode.removeChild(adDiv").append(i).append(");");
//            js += "var adDiv" + i + "= document.getElementById('" + divs[i] + "');if(adDiv" + i + " != null)adDiv" + i + ".parentNode.removeChild(adDiv" + i + ");";
		}
		return js.toString();
	}

	/**
	 * 根据格式，格式化string
	 *
	 * @param format string的格式
	 * @param args   传来的参数
	 * @return 格式后的string
	 */
	public static String strFormat(@NonNull String format, Object... args) {
		return String.format(Locale.CHINESE, format, args);
	}

	/**
	 * 按照 2018-02-08 21:01:36的格式，格式化long时间为string
	 *
	 * @param time long类型的time
	 * @return string的格式化后的时间
	 */
	public static String timeFormat(long time) {
		//时间值为秒，则 * 1000
		if (Long.toString(time).length() == 10) {
			time *= 1000;
		}
		DateFormat format = new SimpleDateFormat(FORMAT_TYPE_DATE_TIME_COMMON, Locale.CHINESE);
		return format.format(new Date(time));
	}

	/**
	 * 将aqi的string形式的时间值 "2018-02-09T12:00:00+00:00" 转化为long的毫秒值
	 *
	 * @param strDate string 类型的aqi时间值
	 * @return long类型的毫秒, 转为北京时区的long，即 0 时区 +8 小时
	 */
	private static long strDate2CNLong(@NonNull String strDate) {
		DateFormat format = new SimpleDateFormat(FORMAT_TYPE_AQI_FORECAST, Locale.CHINESE);
		Date date = new Date();
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//北京时间，在0 时区时间上，+ 8 小时
		return date.getTime() + 8 * 60 * 60 * 1000;
	}

	/**
	 * 获取aqi预报的日期格式
	 *
	 * @param time long类型的time
	 * @return string格式化的日期，如 星期六 10，其中10 标识日期
	 */
	private static String weekDayFormat(@NonNull Context context, long time) {
		DateFormat format = new SimpleDateFormat("d", Locale.CHINESE);
		String day = format.format(time);
		String weekDay = getWeekDay(context, time);
		return strFormat("%s %s", weekDay, day);
	}

	/**
	 * 将从html解析的aqibean数据对象，转化为simple中需要的aqibean
	 *
	 * @param beans 原始aqibean
	 * @return 简单的aqibean
	 */
	public static List<SimpleAQIBean> getSimpleAQiList(@NonNull Context context, @NonNull List<AQIModel.ForecastBean.AqiBean> beans) {
		List<SimpleAQIBean> list = new ArrayList<>();
		//获取所有，星期，日期的set集合,linked HashSet,链表结构，可以维持插入顺序
		Set<String> weekDaySet = new LinkedHashSet<>();
		String weekDay;
		for (AQIModel.ForecastBean.AqiBean bean : beans) {
			//aqibean 的时间，string转化为long，再 +8小时，为北京时间的long毫秒
			long cnLong = strDate2CNLong(bean.getT());
			weekDay = weekDayFormat(context, cnLong);
			weekDaySet.add(weekDay);
		}
		//获取同一日期下，所有aqi数据的汇集的set集合
		Set<Integer> aqiSet;
		SimpleAQIBean simpleAQIBean;
		for (String day : weekDaySet) {
			simpleAQIBean = new SimpleAQIBean();
			aqiSet = new HashSet<>();
			simpleAQIBean.setWeekDay(day);
			for (AQIModel.ForecastBean.AqiBean bean : beans) {
				long cnLong = strDate2CNLong(bean.getT());
				weekDay = weekDayFormat(context, cnLong);
				if (TextUtils.equals(day, weekDay)) {
					aqiSet.addAll(bean.getV());
				}
			}
			Integer min = Collections.min(aqiSet);
			Integer max = Collections.max(aqiSet);
			simpleAQIBean.setRange(strFormat("%d - %d", min, max));
			simpleAQIBean.setAqi(max);
			list.add(simpleAQIBean);
		}
		return list;
	}

	/**
	 * @param context   context
	 * @param firstTime 数据开始时间
	 * @param nowTime   long类型的时间戳
	 * @return 返回 “星期五 8-18:00”这样格式的string
	 */
	public static String weekDayTime(@NonNull Context context, long firstTime, long nowTime) {
		//时间值为秒，则 * 1000 转化为毫秒
		if (Long.toString(nowTime).length() == 10) {
			nowTime *= 1000;
		}
		if (Long.toString(firstTime).length() == 10) {
			firstTime *= 1000;
		}
		SimpleDateFormat startFormat = new SimpleDateFormat("H", Locale.CHINESE);
		SimpleDateFormat endFormat = new SimpleDateFormat("HH:mm", Locale.CHINESE);
		String startTime = startFormat.format(firstTime);
		String endTime = endFormat.format(nowTime);
		//添加星期几
		String weekDay = getWeekDay(context, nowTime);
		return String.format(Locale.CHINESE, "(%s %s - %s)", weekDay, startTime, endTime);
	}

	/**
	 * 根据星期中的天数获取对应星期
	 *
	 * @param context context
	 * @param time    long类型的时间，毫秒
	 * @return 中文的星期几
	 */
	private static String getWeekDay(@NonNull Context context, long time) {
		if (Long.toString(time).length() == 10) {
			time *= 1000;
		}
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		int day = instance.get(Calendar.DAY_OF_WEEK);
		String weekDay;
		switch (day) {
			case Calendar.SUNDAY:
				weekDay = context.getString(R.string.str_sunday);
				break;
			case Calendar.MONDAY:
				weekDay = context.getString(R.string.str_monday);
				break;
			case Calendar.TUESDAY:
				weekDay = context.getString(R.string.str_tuesday);
				break;
			case Calendar.WEDNESDAY:
				weekDay = context.getString(R.string.str_wednesday);
				break;
			case Calendar.THURSDAY:
				weekDay = context.getString(R.string.str_thursday);
				break;
			case Calendar.FRIDAY:
				weekDay = context.getString(R.string.str_friday);
				break;
			case Calendar.SATURDAY:
				weekDay = context.getString(R.string.str_saturday);
				break;
			default:
				weekDay = context.getString(R.string.str_sunday);
				break;
		}
		return weekDay;
	}

	/**
	 * 正则匹配
	 *
	 * @param regex 匹配正则表达式
	 * @param text  待匹配string
	 * @return true 匹配
	 */
	public static boolean isMatch(@NonNull String regex, @NonNull String text) {
		Pattern pattern = Pattern.compile(regex);
		// 忽略大小写的写法
//        Pattern pat = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		// 字符串是否与正则表达式相匹配
		return matcher.matches();
	}

	/**
	 * 根据指定的正则，返回匹配到的内容
	 *
	 * @param regex 正则格式
	 * @param text  待匹配内容
	 * @return 返回所有匹配的数据list
	 */
	public static List<String> getMatched(String regex, String text) {
		List<String> matches = new ArrayList<>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			matches.add(matcher.group());
		}
		return matches;
	}

	/**
	 * 判断设备是否是中文环境
	 *
	 * @return true 中文，false 非中文环境
	 */
	public static boolean isChinese() {
		String language = Locale.getDefault().getLanguage();
		return TextUtils.equals("zh", language);
	}

	/**
	 * 获取AQI网站可支持的语言，若不支持，默认中文
	 *
	 * @return 可用的语言i18n代码
	 */
	public static String getSupportedLanguage() {
		//获取机型设置语言，但网站支持语言种类有限，若不支持的话，默认为中文
		String[] languages = {"en", "cn", "jp", "es", "kr", "ru", "hk", "fr", "pl", "de", "pt", "vn"};
		String language = Locale.getDefault().getLanguage();//获取的中文，是zh，而不是cn，
		if (!Arrays.asList(languages).contains(language)) {
			language = "cn";
		}
		return language;
	}

	/**
	 * 从站点curl中解析出需要的字段
	 * 因为AQI网站的language都是两位数的cn，uk，us之类的格式所以，可不用正则那么麻烦"(\\/city\\/)[a-zA-Z\\.0-9\\/]+(\\/cn)"
	 *
	 * @param url http://aqicn.org/city/beijing/yizhuangkaifaqu/cn/m/ 这个样式的url
	 * @return 返回出city/ 。。。/cn之间的字符串
	 */
	public static String parserStation(@NonNull String url) {
		return url.substring(22, url.length() - 6);
	}

	/**
	 * 请求网络错误信息的合理化展示
	 *
	 * @param error 原始错误信息
	 * @return 人性化的提示信息
	 */
	public static String convertError(@NonNull String error) {
		String tips;
		switch (error) {
			case "SSL handshake timed out"://ssl握手超时
			case "connect timed out"://链接超时
			case "failed to connect to aqicn.org/106.186.25.131 (port 443) after 10000ms"://链接服务器超时
				tips = "服务器连接超时";
				break;
			case "HTTP 404 Not Found"://网络请求错误
				tips = "服务器404错误";
				break;
			case "Unable to resolve host \"api.waqi.info\": No address associated with hostname":
			case "Unable to resolve host \"aqicn.org\": No address associated with hostname":
			case "Unable to resolve host \"raw.githubusercontent.com\": No address associated with hostname":
				tips = "网络错误,请检查网络连接是否正常";
				break;
			default:
				tips = error;
				break;
		}
		return tips;
	}
}

