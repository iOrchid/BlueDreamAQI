package `in`.zhiwei.aqi.activity

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.adapter.SearchCityAdapter
import `in`.zhiwei.aqi.contract.ISearchContract
import `in`.zhiwei.aqi.global.GlobalConstants
import `in`.zhiwei.aqi.network.res.SearchRes
import `in`.zhiwei.aqi.presenter.SearchPresenter
import `in`.zhiwei.aqi.utils.PinyinUtils
import `in`.zhiwei.aqi.utils.Tools
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnEditorAction
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import java.util.*

/**
 * widget配置的界面，在首次添加widget的时候，触发
 * The configuration screen for the [AqiWidget] AppWidget.
 */
/**
 * 构造函数
 */
class SearchStationActivity : AppCompatActivity(), ISearchContract.ISearchView,
    SearchCityAdapter.OnStationItemClickListener {

    @BindView(R.id.et_search_widget)
    lateinit var etSearch: EditText//输入的editText
    @BindView(R.id.iv_back_widget)
    lateinit var ivBack: ImageView//返回
    @BindView(R.id.iv_search_widget)
    lateinit var ivSearch: ImageView//搜索
    @BindView(R.id.pb_widget)
    lateinit var mProgressBar: ProgressBar//progressBar
    @BindView(R.id.rv_cities_widget)
    lateinit var mRecyclerView: RecyclerView//匹配站点的list

    private lateinit var mPresenter: SearchPresenter//搜索的presenter
    private lateinit var mAdapter: SearchCityAdapter//适配器

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_configure)
        ButterKnife.bind(this)
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        mPresenter = SearchPresenter(this)
        //列表控件 配置
        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        mAdapter = SearchCityAdapter()
        mRecyclerView.adapter = mAdapter
        //set listener
        mAdapter.setItemListener(this)

    }

    @OnClick(R.id.iv_search_widget, R.id.iv_clear_widget, R.id.iv_back_widget)
    fun doClick(view: View) {
        when (view.id) {
            R.id.iv_search_widget//搜索城市
            -> searchCity()
            R.id.iv_clear_widget -> etSearch.setText("")
            R.id.iv_back_widget//返回
            -> {
                KeyboardUtils.hideSoftInput(view)
                finish()
            }
            else -> {
                //do nothing
            }
        }
    }

    @OnEditorAction(R.id.et_search_widget)
    fun onEditor(v: TextView, actionId: Int, event: KeyEvent): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //尝试登录
            KeyboardUtils.hideSoftInput(v)
            searchCity()
            return true
        }
        return false
    }

    /**
     * 搜索城市
     */
    private fun searchCity() {
        val city = etSearch.text.toString()
        if (TextUtils.isEmpty(city)) {
            ToastUtils.showShort(getString(R.string.str_input_city))
            return
        }
        //调用搜索
        mPresenter.searchCity(city)
        KeyboardUtils.hideSoftInput(this)
        //显示进度
        mProgressBar.visibility = View.VISIBLE
    }

    override fun onSearchSuccess(results: List<SearchRes.ResultsBean>) {
        val stations = ArrayList<String>()
        for (result in results) {
            stations.add(result.n!![0])
        }
        mAdapter.setData(stations)
        //隐藏进度
        mProgressBar.visibility = View.INVISIBLE
    }

    override fun onSearchFailed(message: String) {
        //隐藏进度
        mProgressBar.visibility = View.INVISIBLE
        ToastUtils.showShort(Tools.convertError(message))
    }

    override fun onItemClicked(name: String) {
        //保存选择的城市, pinyin
        val city = PinyinUtils.ccs2Pinyin(name, "")
        SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, name)
        SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_ID, city)
        //回传
        val intent = Intent()
        intent.putExtra(MainActivity.INTENT_KEY_STATION_NAME, city)
        setResult(Activity.RESULT_OK, intent)
        //关闭
        finish()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.dispose()
    }
}

