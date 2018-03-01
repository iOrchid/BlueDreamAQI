package in.zhiwei.aqi;

import android.app.Application;
import android.content.Intent;

import com.blankj.utilcode.util.Utils;
import com.squareup.leakcanary.LeakCanary;

import in.zhiwei.aqi.service.LocationService;

/**
 * App的Application，用于初始化基础数据配置
 * Author: gzw48760.
 * Date: 2018/2/1 0001,14:39.
 */

public class AQIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化配置
        Utils.init(this);
        LeakCanary.install(this);
        //启动定位服务
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }
}