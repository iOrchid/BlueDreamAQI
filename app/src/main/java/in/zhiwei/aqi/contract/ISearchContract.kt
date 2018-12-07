package `in`.zhiwei.aqi.contract

import `in`.zhiwei.aqi.BasePresenter
import `in`.zhiwei.aqi.BaseView
import `in`.zhiwei.aqi.network.res.SearchRes

/**
 * Author: gzw48760.
 * Date: 2018/4/10 0010,15:35.
 */
interface ISearchContract {
    /**
     * 城市搜索的view接口
     */
    interface ISearchView : BaseView<ISearchPresenter> {
        /**
         * 搜索成功，，获取结果
         *
         * @param results 匹配的城市、站点名称
         */
        fun onSearchSuccess(results: List<SearchRes.ResultsBean>)

        /**
         * 搜索失败
         *
         * @param message 错误信息
         */
        fun onSearchFailed(message: String)
    }

    /**
     * 城市搜索的presenter接口
     */
    interface ISearchPresenter : BasePresenter
}
