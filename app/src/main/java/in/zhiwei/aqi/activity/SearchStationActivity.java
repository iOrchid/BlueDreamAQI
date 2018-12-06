package in.zhiwei.aqi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import in.zhiwei.aqi.R;
import in.zhiwei.aqi.adapter.SearchCityAdapter;
import in.zhiwei.aqi.contract.ISearchContract;
import in.zhiwei.aqi.global.GlobalConstants;
import in.zhiwei.aqi.network.res.SearchRes;
import in.zhiwei.aqi.presenter.SearchPresenter;
import in.zhiwei.aqi.utils.PinyinUtils;
import in.zhiwei.aqi.utils.Tools;
import in.zhiwei.aqi.widgets.AqiWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * widget配置的界面，在首次添加widget的时候，触发
 * The configuration screen for the {@link AqiWidget AqiWidget} AppWidget.
 */
public class SearchStationActivity extends AppCompatActivity implements ISearchContract.ISearchView, SearchCityAdapter.OnStationItemClickListener {

	@BindView(R.id.et_search_widget)
	EditText etSearch;//输入的editText
	@BindView(R.id.iv_back_widget)
	ImageView ivBack;//返回
	@BindView(R.id.iv_search_widget)
	ImageView ivSearch;//搜索
	@BindView(R.id.pb_widget)
	ProgressBar mProgressBar;//progressBar
	@BindView(R.id.rv_cities_widget)
	RecyclerView mRecyclerView;//匹配站点的list

	private SearchPresenter mPresenter;//搜索的presenter
	private SearchCityAdapter mAdapter;//适配器

	/**
	 * 构造函数
	 */
	public SearchStationActivity() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widget_configure);
		ButterKnife.bind(this);
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		mPresenter = new SearchPresenter(this);
		//列表控件 配置
		LinearLayoutManager lm = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(lm);
		mAdapter = new SearchCityAdapter();
		mRecyclerView.setAdapter(mAdapter);
		//set listener
		mAdapter.setItemListener(this);

	}

	@OnClick({R.id.iv_search_widget, R.id.iv_clear_widget, R.id.iv_back_widget})
	public void doClick(View view) {
		switch (view.getId()) {
			case R.id.iv_search_widget://搜索城市
				searchCity();
				break;
			case R.id.iv_clear_widget:
				etSearch.setText("");
				break;
			case R.id.iv_back_widget://返回
				KeyboardUtils.hideSoftInput(view);
				finish();
				break;
			default:
				break;
		}
	}

	@OnEditorAction(R.id.et_search_widget)
	public boolean onEditor(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			//尝试登录
			KeyboardUtils.hideSoftInput(v);
			searchCity();
			return true;
		}
		return false;
	}

	/**
	 * 搜索城市
	 */
	private void searchCity() {
		String city = etSearch.getText().toString();
		if (TextUtils.isEmpty(city)) {
			ToastUtils.showShort(getString(R.string.str_input_city));
			return;
		}
		//调用搜索
		mPresenter.searchCity(city);
		KeyboardUtils.hideSoftInput(this);
		//显示进度
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onSearchSuccess(@NonNull List<SearchRes.ResultsBean> results) {
		List<String> stations = new ArrayList<>();
		for (SearchRes.ResultsBean result : results) {
			stations.add(result.getN().get(0));
		}
		mAdapter.setData(stations);
		//隐藏进度
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onSearchFailed(@NonNull String message) {
		//隐藏进度
		mProgressBar.setVisibility(View.INVISIBLE);
		ToastUtils.showShort(Tools.convertError(message));
	}

	@Override
	public void onItemClicked(@NonNull String name) {
		//保存选择的城市, pinyin
		String city = PinyinUtils.ccs2Pinyin(name, "");
		SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, name);
		SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_ID, city);
		//回传
		Intent intent = new Intent();
		intent.putExtra(MainActivity.INTENT_KEY_STATION_NAME, city);
		setResult(RESULT_OK, intent);
		//关闭
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mPresenter.dispose();
	}
}

