package in.zhiwei.aqi.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;

import in.zhiwei.aqi.global.GlobalConstants;
import in.zhiwei.aqi.network.AQIService;
import in.zhiwei.aqi.network.HttpApi;
import in.zhiwei.aqi.utils.Tools;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 用于根据ip获取粗略城市定位的
 * Author: gzw48760.
 * Date: 2018/2/26 0026,15:38.
 */

public class LocationService extends IntentService {

    /**
     * 创建一个intentService，默认构造函数，内部调用super的构造函数，并设置一个线程的名称
     */
    public LocationService() {
        super("aqi_location");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        HttpApi.getInstance().create(AQIService.class)
                .getNearestStation("")//城市编号，暂时v1.0版本不写
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nearestRes -> {
                    String city = nearestRes.getG().getCity();
                    String cityName;
                    if (Tools.isChinese()) {
                        cityName = nearestRes.getG().getNames().getZhCN();
                    } else {
                        cityName = nearestRes.getG().getNames().getEn();
                    }
                    if (!TextUtils.isEmpty(city)) {
                        //缓存当前城市信息
                        SPUtils.getInstance().put(GlobalConstants.SP_CURRENT_CITY_ID, city);
                        SPUtils.getInstance().put(GlobalConstants.SP_CURRENT_CITY_NAME, cityName);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Log.e("test", "throwable:: " + throwable.getMessage());
                });
    }
}
