package `in`.zhiwei.aqi.contract

import `in`.zhiwei.aqi.BasePresenter
import `in`.zhiwei.aqi.BaseView
import `in`.zhiwei.aqi.entity.AQIEntity

/**
 * 城市AQI预报界面的契约管理接口
 * Author: gzw48760.
 * Date: 2018/2/6 0006,10:53.
 */

interface IAQIContract {
    /**
     * 城市AQI界面view接口
     */
    interface IAQIView : BaseView<IAQIPresenter> {
        /**
         * 获取aqi数据成功
         *
         * @param aqiModel aqi数据对象
         */
        fun onGetAQISuccess(aqiEntity: AQIEntity)

        /**
         * 获取aqi数据失败
         *
         * @param error 错误信息
         */
        fun onGetAQIFailed(error: String)

        /**
         * 检查升级成功
         * @param hasNewVersion 是否有新的版本
         */
        fun onCheckUpgradeSuccess(hasNewVersion: Boolean)

        /**
         * 检查升级失败
         *
         * @param error 错误码
         */
        fun onCheckUpgradeFailed(error: String)

    }

    /**
     * 城市AQI业务控制presenter
     */
    interface IAQIPresenter : BasePresenter
}
