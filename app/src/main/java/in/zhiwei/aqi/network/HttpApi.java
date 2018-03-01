package in.zhiwei.aqi.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 封装retrofit的网络请求
 * Author: gzw48760.
 * Date: 2018/2/11 0011,18:03.
 */

public class HttpApi {
    private static final String URL_AQICN_ORG = "https://aqicn.org/";//AQI 数据源aqicn的base url
    private static Retrofit mApi;//单例模式，对象

    /**
     * 私有化构造函数
     */
    private HttpApi() {
    }

    /**
     * 公开提供获取单例对象
     *
     * @return httpApi对象
     */
    public static Retrofit getInstance() {
        if (mApi == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            mApi = builder.baseUrl(URL_AQICN_ORG)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mApi;
    }

    /**
     * 用于创建api接口service
     *
     * @param service 泛型的service
     * @param <T>     类型
     * @return 返回泛型
     */
    public <T> T create(Class<T> service) {
        return mApi.create(service);
    }
}
