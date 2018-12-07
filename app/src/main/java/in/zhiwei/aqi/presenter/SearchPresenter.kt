package `in`.zhiwei.aqi.presenter

import `in`.zhiwei.aqi.contract.ISearchContract
import `in`.zhiwei.aqi.network.AQIService
import `in`.zhiwei.aqi.network.HttpApi
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 搜索城市列表的presenter
 * Author: gzw48760.
 * Date: 2018/4/10 0010,15:38.
 */
class SearchPresenter
/**
 * 构造函数
 *
 * @param searchView view的实现类对象
 */
    (private val mSearchView: ISearchContract.ISearchView) : ISearchContract.ISearchPresenter {
    private var disposable: Disposable? = null

    override fun start() {
        searchCity("beijing")
    }

    fun searchCity(name: String) {
        val http = HttpApi.instance.create(AQIService::class.java)
        disposable = http.searchStation(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchRes ->
                Log.d("SearchPresenter", "searchRes:" + searchRes.results!!.get(0).n!!.get(0))
                mSearchView.onSearchSuccess(searchRes.results!!)
            }, { throwable ->
                mSearchView.onSearchFailed(throwable.message!!)
                throwable.printStackTrace()

            })
    }

    /**
     * 关闭资源
     */
    fun dispose() {
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}
