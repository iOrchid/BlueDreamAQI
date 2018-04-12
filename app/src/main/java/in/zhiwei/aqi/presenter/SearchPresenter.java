package in.zhiwei.aqi.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import in.zhiwei.aqi.contract.ISearchContract;
import in.zhiwei.aqi.network.AQIService;
import in.zhiwei.aqi.network.HttpApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 搜索城市列表的presenter
 * Author: gzw48760.
 * Date: 2018/4/10 0010,15:38.
 */
public class SearchPresenter implements ISearchContract.ISearchPresenter {

	private ISearchContract.ISearchView mSearchView;
	private Disposable disposable;

	/**
	 * 构造函数
	 *
	 * @param searchView view的实现类对象
	 */
	public SearchPresenter(ISearchContract.ISearchView searchView) {
		this.mSearchView = searchView;
	}

	@Override
	public void start() {
		searchCity("beijing");
	}

	public void searchCity(@NonNull String name) {
		AQIService http = HttpApi.getInstance().create(AQIService.class);
		disposable = http.searchStation(name)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(searchRes -> {
					Log.d("SearchPresenter", "searchRes:" + searchRes.getResults().get(0).getN().get(0));
					mSearchView.onSearchSuccess(searchRes.getResults());
				}, throwable -> {
					mSearchView.onSearchFailed(throwable.getMessage());
					throwable.printStackTrace();

				});
	}

	/**
	 * 关闭资源
	 */
	public void dispose(){
		if (disposable!=null){
			disposable.dispose();
		}
	}
}
