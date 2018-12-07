package `in`.zhiwei.aqi

import com.blankj.utilcode.util.RegexUtils

import org.junit.Test

import java.io.IOException
import java.util.ArrayList
import java.util.HashSet

import `in`.zhiwei.aqi.utils.Tools
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

import org.junit.Assert.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun testReg() {
        var text = "<div style=\"color:#888;text-align:center;font-size:13px;padding:5px;background-color:white;\">\n" +
                "                                                    Temp.: \n" +
                "                                                    <span class=\"temp\" format=\"b\" temp=\"10\"><b>10</b>°C</span> - Wind: \n" +
                "                                                    <b>3</b>m/s S - \n" +
                "                                                   </div>"
        text = "http://aqicn.org/city/beijing/yizhuangkaifaqu/cn/m/"
        text = text.replace("[\\r\\n ]".toRegex(), "")
        //        System.out.println(text);
        val reg2 = "(\\/city\\/)[a-zA-Z\\.0-9\\/]+(\\/cn)"
        //        reg2 = "[\\s\\S]*?google[\\s\\S]*?";
        val matches = RegexUtils.getMatches(reg2, text)
        for (s in matches) {
            println(s)
        }
        println(Tools.parserStation(text))
    }

    @Test
    fun testSet() {
        val set = HashSet<String>()
        val list = ArrayList<String>()
        list.add("abc")
        list.add("deef")
        set.add("abc")
        set.add("dbc")
        set.add("abc")
        set.addAll(list)
        for (s in set) {
            println(s)
        }
    }

    @Test
    fun testOkhttp3() {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            //                .url("http://zhiwei.in/update.json")
            //                .url("https://wind.waqi.info/nsearch/station/he")
            //                .url("https://api.waqi.info/mapq/nearest?from=1451")
            .url("https://aqicn.org/map/shanghai/m/")
            .build()
        val call = okHttpClient.newCall(request)
        var response: Response? = null
        try {
            response = call.execute()
            val body = response!!.body()
            println(body!!.string())
        } catch (e: IOException) {
            e.printStackTrace()
            println(e.message)
        }

    }
}
