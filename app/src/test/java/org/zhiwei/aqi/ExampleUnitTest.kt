package org.zhiwei.aqi

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
	@Test
	fun addition_isCorrect() {
		assertEquals(4, 2 + 2)
	}

	@Test
	fun testRegex() {
		val compile = Pattern.compile("(?<=\\()(.+?)(?=\\))")
		val matcher =
			compile.matcher("background:url(/source/template/default/wap/images/level2/3.jpg) no-repeat;background-position:center; background-size:420px 332px;")
		while (matcher.find()) {
			println("testRegex 25行: ${matcher.group()}")
		}
	}
}
