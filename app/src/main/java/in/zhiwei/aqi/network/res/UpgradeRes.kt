package `in`.zhiwei.aqi.network.res

/**
 * 检查升级的json对象类
 * Author: gzw48760.
 * Date: 2018/2/28 0028,20:32.
 */

class UpgradeRes {

    /**
     * appName : 梦之蓝AQI
     * forceUpgrade : true
     * updateUrl : http://zhiwei.net.cn/apk/aqi.apk
     * upgradeInfo : V2.0版本更新，你就不想试一试么 @_@,~_~
     * versionNum : 1
     */

    var appName: String? = null
    var isForceUpgrade: Boolean = false
    var updateUrl: String? = null
    var upgradeInfo: String? = null
    var versionNum: Int = 0
}
