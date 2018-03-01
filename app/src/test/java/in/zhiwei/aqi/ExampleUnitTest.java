package in.zhiwei.aqi;

import com.blankj.utilcode.util.RegexUtils;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.zhiwei.aqi.utils.Tools;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testReg() {
        String text = "<div style=\"color:#888;text-align:center;font-size:13px;padding:5px;background-color:white;\">\n" +
                "                                                    Temp.: \n" +
                "                                                    <span class=\"temp\" format=\"b\" temp=\"10\"><b>10</b>°C</span> - Wind: \n" +
                "                                                    <b>3</b>m/s S - \n" +
                "                                                   </div>";
        text = "http://aqicn.org/city/beijing/yizhuangkaifaqu/cn/m/";
        text = text.replaceAll("[\\r\\n ]", "");
//        System.out.println(text);
        String reg2 = "(\\/city\\/)[a-zA-Z\\.0-9\\/]+(\\/cn)";
//        reg2 = "[\\s\\S]*?google[\\s\\S]*?";
        List<String> matches = RegexUtils.getMatches(reg2, text);
        for (String s : matches) {
            System.out.println(s);
        }
        System.out.println(Tools.parserStation(text));
    }

    @Test
    public void testSet() {
        Set<String> set = new HashSet<>();
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("deef");
        set.add("abc");
        set.add("dbc");
        set.add("abc");
        set.addAll(list);
        for (String s : set) {
            System.out.println(s);
        }
    }

    @Test
    public void testOkhttp3(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
//                .url("http://zhiwei.in/update.json")
//                .url("https://wind.waqi.info/nsearch/station/he")
//                .url("https://api.waqi.info/mapq/nearest?from=1451")
                .url("https://aqicn.org/map/shanghai/m/")
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            ResponseBody body = response.body();
            System.out.println(body.string());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
