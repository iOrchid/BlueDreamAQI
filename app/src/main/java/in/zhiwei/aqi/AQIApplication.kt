package `in`.zhiwei.aqi

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.squareup.leakcanary.LeakCanary

/**
 * App的Application，用于初始化基础数据配置
 * Author: gzw48760.
 * Date: 2018/2/1 0001,14:39.
 */

class AQIApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化配置
        Utils.init(this)
        LeakCanary.install(this)
    }
}
