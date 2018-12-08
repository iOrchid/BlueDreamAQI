package `in`.zhiwei.aqi.activity

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.adapter.NearbyAdapter
import `in`.zhiwei.aqi.adapter.SimpleAQIAdapter
import `in`.zhiwei.aqi.contract.IAQIContract
import `in`.zhiwei.aqi.entity.AQIEntity
import `in`.zhiwei.aqi.entity.AQIModel
import `in`.zhiwei.aqi.global.GlobalConstants
import `in`.zhiwei.aqi.network.AQIService
import `in`.zhiwei.aqi.network.HttpApi
import `in`.zhiwei.aqi.presenter.MainPresenter
import `in`.zhiwei.aqi.utils.Tools
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.*
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 主界面activity，内部嵌套fragment，用于展示城市aqi信息，以及选择城市等
 */
class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, IAQIContract.IAQIView,
    NearbyAdapter.OnNearbyItemClickListener {
    @BindView(R.id.toolbar_main)
    lateinit var mToolbar: Toolbar//toolbar
    @BindView(R.id.srl_main)
    lateinit var mRefreshLayout: SwipeRefreshLayout//刷新的layout
    @BindView(R.id.ll_aqi_main)
    lateinit var mLinearLayout: LinearLayout//aqi的主体布局
    @BindView(R.id.nsv_main)
    lateinit var mNestedScrollView: NestedScrollView//滚动布局
    @BindViews(R.id.btn_error_main, R.id.iv_airship_main)
    lateinit var viewError: Array<View>//遇到网络错误view控制

    @BindView(R.id.tv_api_num_header)
    lateinit var tvAQINumHeader: TextView//header 中aqi数字
    @BindView(R.id.tv_api_level_name_header)
    lateinit var tvAQILevelNameHeader: TextView//header中aqi level
    @BindView(R.id.tv_api_update_time_header)
    lateinit var tvAQIUpdateTimeHeader: TextView//header中，更新数据的时间
    @BindView(R.id.tv_temperature_api_fragment)
    lateinit var tvAQITemperature: TextView//温度和风速
    @BindView(R.id.tv_tips_header)
    lateinit var tvTips: TextView//跑马灯效果的提示
    @BindView(R.id.cl_header_api_main)
    lateinit var clAQIHeader: ConstraintLayout//header布局


    @BindView(R.id.rv_simple_aqi_fragment)
    lateinit var mRvSimpleAQI: RecyclerView//星期AQI数据的recyclerView
    @BindView(R.id.rv_nearby_city_aqi_fragment)
    lateinit var mRvNearby: RecyclerView//附近监测站点
    @BindView(R.id.tv_city_time_weather_data)
    lateinit var tvCityTimeWeatherData: TextView//城区状况，更新时间
    @BindView(R.id.tv_temperature_weather_data)
    lateinit var tvTemperatureWeatherData: TextView//温度数值
    @BindView(R.id.tv_humidity_weather_data)
    lateinit var tvHumidityWeatherData: TextView//湿度
    @BindView(R.id.tv_air_pressure_weather_data)
    lateinit var tvAirPressureWeatherData: TextView//空气压力

    private lateinit var mNearAdapter: NearbyAdapter//附近站点的adapter
    private lateinit var mSimpleAQIAdapter: SimpleAQIAdapter//简单AQI数据预报的数据适配器

    private var mTemperature: Int = 0//温度
    private var mWindSpeed: Int = 0//风速
    private var mHumidity: Int = 0//湿度
    private var mAirPressure: Int = 0//压强
    private var disposable: Disposable? = null

    private lateinit var mPresenter: MainPresenter//用于控制fragment UI的presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //ButterKnife init
        ButterKnife.bind(this)
        //toolsBar,使用toolbar，就需要设置app的theme为noActionbar，
        initToolbar()
        //服务责任协议
        val isAgree = SPUtils.getInstance().getBoolean(GlobalConstants.SP_KEY_IS_AGREE_TIPS)
        if (!isAgree) {
            initTips()
        }
        //长按钮提示
        val hasShowed = SPUtils.getInstance().getBoolean(GlobalConstants.SP_KEY_HAS_SHOWED)
        if (hasShowed) {
            tvTips.visibility = View.INVISIBLE
        } else {
            tvTips.visibility = View.VISIBLE
            tvTips.isSelected = true
        }
        //initData
        initData()
        //
        mPresenter = MainPresenter(this)
        val html = SPUtils.getInstance().getString(GlobalConstants.SP_KEY_AQI_SERVER_DATA)
        if (!html.isEmpty()) {
            mPresenter.parserHtml(html)
        }
        //首次进来就开始请求网络
        onRefresh()
        //检查升级
        mPresenter.checkUpgrade(this)
    }

    /**
     * 提示服务协议
     */
    private fun initTips() {
        AlertDialog.Builder(this).setTitle(R.string.str_service_tips)
            .setCancelable(false)
            .setMessage(getString(R.string.str_content_tips))
            .setPositiveButton(getString(R.string.str_ok)) { _, _ ->
                SPUtils.getInstance().put(GlobalConstants.SP_KEY_IS_AGREE_TIPS, true)
            }
            .setNegativeButton(getString(R.string.str_cancel)) { _, _ -> finish() }
            .show()
    }

    /**
     * 配置toolBar
     */
    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        //获取当前城市的name
        val cityName =
            SPUtils.getInstance().getString(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, getString(R.string.str_beijing))
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher_round)
            actionBar.title = cityName
        }
    }

    /**
     * initData
     */
    private fun initData() {
        //简单的AQI预报的RecyclerView配置
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        mRvSimpleAQI.layoutManager = lm
        mSimpleAQIAdapter = SimpleAQIAdapter()
        mRvSimpleAQI.adapter = mSimpleAQIAdapter
        //附近监测站点recyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        mRvNearby.layoutManager = layoutManager
        mNearAdapter = NearbyAdapter()
        //注册监听，item点击
        mNearAdapter.setItemListener(this)
        mRvNearby.adapter = mNearAdapter
        //避免和scrollView滑动冲突
        mRvSimpleAQI.isNestedScrollingEnabled = false
        mRvNearby.isNestedScrollingEnabled = false
        //refreshlayout
        mRefreshLayout.setOnRefreshListener(this)
    }

    @OnLongClick(R.id.cl_header_api_main, R.id.toolbar_main)
    fun doLongClick(view: View): Boolean {
        when (view.id) {
            R.id.cl_header_api_main -> {
                val dialog = AlertDialog.Builder(this)
                    .setView(R.layout.pop_aqi_level_description).create()
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                //保存状态，隐藏 长按 提示
                SPUtils.getInstance().put(GlobalConstants.SP_KEY_HAS_SHOWED, true)
            }
            R.id.toolbar_main -> {
                //				ToastUtils.showShort("热修复，添加的补丁，长按显示");
                //				SophixManager.getInstance().queryAndLoadNewPatch();
                //				ToastUtils.showShort("去权限后、请求热修复补丁，加载");
            }
            else -> {
                //do nothing
            }
        }
        return false
    }

    @OnClick(R.id.btn_error_main)
    fun doClick(view: View) {
        onRefresh()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.location_menu//定位到当前位置
            -> if (checkNetwork()) {
                getLocation()
            }
            R.id.map_menu//查看aqi地图数据
            -> if (checkNetwork()) {
                val city = SPUtils.getInstance().getString(GlobalConstants.SP_KEY_CURRENT_CITY_ID, "beijing")
                val intent = Intent(this, CityAQIMapActivity::class.java)
                intent.putExtra("city_id", city)
                startActivity(intent)
            }
            R.id.share_menu//分享
            -> shareIt()
            R.id.search_menu//搜索城市
            -> if (checkNetwork()) {
                val searchIntent = Intent(this, SearchStationActivity::class.java)
                startActivityForResult(searchIntent, SEARCH_CITY_REQUEST_CODE)
            }
            R.id.feedback_menu -> {
                //反馈的菜单
            }
            R.id.about_menu//关于App和作者
            -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.main_in, R.anim.main_out)
            }
            else -> {
                //do nothing
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 校验网络状态
     *
     * @return 可用与否
     */
    private fun checkNetwork(): Boolean {
        return if (NetworkUtils.isAvailableByPing()) {
            true
        } else {
            ToastUtils.showShort("网络似乎不可用哦~_~")
            false
        }
    }

    /**
     * 使用ip定位aqi城市，然后刷新
     */
    private fun getLocation() {
        disposable = HttpApi.instance.create(AQIService::class.java)
            .getNearestStation("")//城市编号，暂时v1.0版本不写
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ nearestRes ->
                val city = nearestRes.g?.city
                val cityName: String? = if (Tools.isChinese) {
                    nearestRes.g?.names?.zhCN
                } else {
                    nearestRes.g?.names?.en
                }
                if (!TextUtils.isEmpty(city) && !TextUtils.isEmpty(cityName)) {
                    //缓存当前城市信息
                    SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_ID, city)
                    SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, cityName)
                }
                //刷新
                onRefresh()
            }, { it.printStackTrace() })
    }

    /**
     * 分享app给朋友
     */
    private fun shareIt() {
        val textIntent = Intent(Intent.ACTION_SEND)
        textIntent.type = "text/plain"
        textIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.str_url_app_download))
        startActivity(Intent.createChooser(textIntent, getString(R.string.str_share_title)))
    }

    override fun onGetAQISuccess(aqiEntity: AQIEntity) {
        val aqiModel = aqiEntity.aqiModel
        val otherAQIData = aqiEntity.otherAQIData
        //header 设置
        setHeaderData(aqiModel)
        //温度，风速设置
        setTempWind(aqiModel, otherAQIData?.windDirection)
        //气象数据
        setWeatherData(aqiModel!!)
        //附近站点监测数据
        mNearAdapter.setData(aqiModel.nearest!!)
        //简单aqi预报list
        val aqiBeans = aqiModel.forecast?.aqi
        val simpleAQiList = Tools.getSimpleAQiList(this, aqiBeans!!)
        mSimpleAQIAdapter.setData(simpleAQiList)
        //设置toolbar的title
        mToolbar.title = aqiModel.city?.name
        //refreshlayout
        mRefreshLayout.isRefreshing = false
        //滚动到顶部
        mNestedScrollView.scrollTo(0, 0)
        //根据网络状态的提示页面显隐控制
        mLinearLayout.visibility = View.VISIBLE
        viewError.forEach { v -> v.visibility = View.INVISIBLE }
    }

    /**
     * 设置风速度，温度数据
     *
     * @param aqiModel      aqi对象
     * @param windDirection
     */
    private fun setTempWind(aqiModel: AQIModel?, windDirection: String?) {
        val iaqi = aqiModel?.iaqi
        for (bean in iaqi!!) {
            when (bean.p) {
                "pm25"//pm2.5
                -> {
                    //do nothing
                }
                "pm10"//pm10
                -> {
                    //do nothing
                }
                "o3"//臭氧O3
                -> {
                    //do nothing
                }
                "no2"//二氧化氮
                -> {
                    //do nothing
                }
                "so2"//二氧化硫
                -> {
                    //do nothing
                }
                "co"//一氧化碳
                -> {
                    //do nothing
                }
                "t"//温度
                -> mTemperature = bean.v!![0]//6,标识温度的数据位置而v内含有，平均值、最小、最大，0位置标识平均值
                "d"//凝露点
                -> {
                    //do nothing
                }
                "p"//大气压强
                -> mAirPressure = bean.v!![0]//8,标识压强
                "h"//湿度
                -> mHumidity = bean.v!![0]//9，标识湿度
                "w"//风速
                -> mWindSpeed = bean.v!![0]//10 标识风速的数据位置
                else -> {
                    //do nothing
                }
            }
        }
        tvAQITemperature.text = Tools.strFormat(
            getString(R.string.str_temperature_wind),
            mTemperature,
            mWindSpeed,
            windDirection
        )//温度和风速
    }

    /**
     * header模块数据
     *
     * @param aqiModel aqi数据对象
     */
    private fun setHeaderData(aqiModel: AQIModel?) {
        val mAQI = aqiModel?.aqi
        tvAQINumHeader.text = Tools.strFormat("%d", mAQI)//aqi值
        tvAQILevelNameHeader.text = Tools.getAQILevel(this, mAQI!!)//污染程度
        //设置字体颜色 aqi > 150 颜色白色，否则，黑色
        val colorBG = if (mAQI > 150) Color.WHITE else Color.BLACK
        tvAQINumHeader.setTextColor(colorBG)
        tvAQILevelNameHeader.setTextColor(colorBG)
        clAQIHeader.setBackgroundColor(resources.getColor(Tools.getAQILevelColor(mAQI)))//背景颜色
        val colorBanner =
            if (mAQI > 150) resources.getColor(R.color.colorWhiteTranslate) else resources.getColor(R.color.colorBlackTranslate)
        //有可能不是中文，会出现 Attempt to invoke virtual method 'java.lang.String in.zhiwei.aqi.entity.AQIModel$TimeBean$SBean$CnBean.getTime()' on a null object reference
        var updateTime = aqiModel.time?.s?.en?.time//先默认英文的时间，如果是是中文环境，在重新赋值
        val cnBean = aqiModel.time?.s?.cn
        if (Tools.isChinese) {
            updateTime = if (cnBean == null) Tools.translateEn(updateTime!!) else cnBean.time
        }
        //首要污染物
        val dominentpol = aqiModel.dominentpol
        val strBanner = if (dominentpol == null) updateTime else Tools.strFormat(
            getString(R.string.str_api_update_time),
            updateTime,
            dominentpol
        )
        tvAQIUpdateTimeHeader.text = strBanner//更新时间
        tvAQIUpdateTimeHeader.setBackgroundColor(colorBanner)
    }

    /**
     * 设置气象数据，板块
     *
     * @param aqiModel aqi的数据对象
     */
    private fun setWeatherData(aqiModel: AQIModel) {
        val sBean = aqiModel.time?.s
        //默认就支持中文，英文
        var updateTime = sBean?.en?.time//先默认英文的时间，如果是是中文环境，在重新赋值
        val cnBean = sBean?.cn
        if (Tools.isChinese) {
            updateTime = if (cnBean == null) Tools.translateEn(updateTime!!) else cnBean.time
        }
        val city = aqiModel.city?.name
        val weekDayTime = updateTime?.substring(4, updateTime.length)
        tvCityTimeWeatherData.text =
                Tools.strFormat(getString(R.string.str_current_weather_data), city, weekDayTime)
        tvTemperatureWeatherData.text = Tools.strFormat(getString(R.string.str_temperature_num), mTemperature)
        tvHumidityWeatherData.text = Tools.strFormat(getString(R.string.str_humidity), mHumidity)
        tvAirPressureWeatherData.text = Tools.strFormat(getString(R.string.str_air_pressure), mAirPressure)
    }

    override fun onGetAQIFailed(error: String) {
        mRefreshLayout.isRefreshing = false
        //根据网络状态的提示页面显隐控制
        if (SPUtils.getInstance().getString(GlobalConstants.SP_KEY_AQI_SERVER_DATA).isEmpty()) {
            //如果缓存了aqi数据，则显示缓存数据，而不是直接跳转到错误界面,否则显示错误信息界面
            mLinearLayout.visibility = View.INVISIBLE
            viewError.forEach { v -> v.visibility = View.VISIBLE }
        }
        ToastUtils.showShort(Tools.convertError(error))
    }

    override fun onCheckUpgradeSuccess(hasNewVersion: Boolean) {
        if (hasNewVersion) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.str_find_new))
                .setMessage(getString(R.string.str_get_new))
                .setNegativeButton(getString(R.string.str_cancel), null)
                .setPositiveButton(getString(R.string.str_ok)) { _, _ -> mPresenter.downloadApk(this@MainActivity) }
                .show()
        } else {
            ToastUtils.showShort(R.string.str_version)
            //			Log.i("MainActivity", "App已是最新版本");
        }
    }

    override fun onCheckUpgradeFailed(error: String) {
        ToastUtils.showShort(error)
    }

    override fun onNearbyItemClicked(station: String) {
        mPresenter.changeStation(station)
        //缓存为current station
        SPUtils.getInstance().put(GlobalConstants.SP_KEY_CURRENT_CITY_ID, station)
        mRefreshLayout.isRefreshing = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SEARCH_CITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val stationName = data?.getStringExtra(INTENT_KEY_STATION_NAME)
            mRefreshLayout.isRefreshing = true
            mPresenter.changeStation(stationName!!)
        }
    }

    override fun onRefresh() {
        //开始网络请求
        mPresenter.start()
        //加载中
        mRefreshLayout.isRefreshing = true
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
        mPresenter.dispose()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.main_in, R.anim.main_out)
    }

    companion object {
        const val SEARCH_CITY_REQUEST_CODE = 100//请求码
        const val INTENT_KEY_STATION_NAME = "station_name"//intent 传值的key
    }
}
