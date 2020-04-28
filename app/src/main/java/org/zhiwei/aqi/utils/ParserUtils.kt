package org.zhiwei.aqi.utils

import java.util.regex.Pattern

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 17:04
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 解析文本的工具
 */
object ParserUtils {

	/**
	 * 正则匹配，获取()之间的string,返回数组
	 */
	fun parseInBrackets(string: String): ArrayList<String> {
		val compile = Pattern.compile("(?<=\\()(.+?)(?=\\))")
		val matcher = compile.matcher(string)
		val arrayList = arrayListOf<String>()
		while (matcher.find()) {
			arrayList.add(matcher.group())
		}
		return arrayList
	}

	/**
	 * 正则匹配，获取()之间的string，返回第一个
	 */
	fun parseInBracketsFirst(string: String): String {
		val compile = Pattern.compile("(?<=\\()(.+?)(?=\\))")
		val matcher = compile.matcher(string)
		val arrayList = arrayListOf<String>()
		while (matcher.find()) {
			arrayList.add(matcher.group())
		}
		return arrayList[0]
	}
}