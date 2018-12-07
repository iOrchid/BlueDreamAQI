package `in`.zhiwei.aqi.service

import android.app.IntentService
import android.content.Intent
import android.text.TextUtils
import android.util.Log

import com.blankj.utilcode.util.SPUtils
import `in`.zhiwei.aqi.global.GlobalConstants
import `in`.zhiwei.aqi.network.AQIService
import `in`.zhiwei.aqi.network.HttpApi
import `in`.zhiwei.aqi.utils.Tools
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 用于根据ip获取粗略城市定位的
 * Author: gzw48760.
 * Date: 2018/2/26 0026,15:38.
 */

/**
 * 创建一个intentService，默认构造函数，内部调用super的构造函数，并设置一个线程的名称
 */
class LocationService : IntentService("aqi_location") {

    private var disposable: Disposable? = null//控制阀

    override fun onHandleIntent(intent: Intent?) {
        disposable = HttpApi.instance.create(AQIService::class.java)
            .getNearestStation("")//城市编号，暂时v1.0版本不写
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ nearestRes ->
                val city = nearestRes.g!!.city
                val cityName: String?
                if (Tools.isChinese) {
                    cityName = nearestRes.g!!.names!!.zhCN
                } else {
                    cityName = nearestRes.g!!.names!!.en
                }
                if (!TextUtils.isEmpty(city)) {
                    //缓存当前城市信息
                    SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_ID, city)
                    SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, cityName)
                }
            }, { throwable ->
                throwable.printStackTrace()
                Log.e("test", "throwable:: " + throwable.message)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}
