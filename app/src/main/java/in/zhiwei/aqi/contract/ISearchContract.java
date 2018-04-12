package in.zhiwei.aqi.contract;

import android.support.annotation.NonNull;

import java.util.List;

import in.zhiwei.aqi.BasePresenter;
import in.zhiwei.aqi.BaseView;
import in.zhiwei.aqi.network.res.SearchRes;

/**
 * Author: gzw48760.
 * Date: 2018/4/10 0010,15:35.
 */
public interface ISearchContract {
	/**
	 * 城市搜索的view接口
	 */
	interface ISearchView extends BaseView<ISearchPresenter> {
		/**
		 * 搜索成功，，获取结果
		 *
		 * @param results 匹配的城市、站点名称
		 */
		void onSearchSuccess(@NonNull List<SearchRes.ResultsBean> results);

		/**
		 * 搜索失败
		 *
		 * @param message 错误信息
		 */
		void onSearchFailed(@NonNull String message);
	}

	/**
	 * 城市搜索的presenter接口
	 */
	interface ISearchPresenter extends BasePresenter {

	}
}
