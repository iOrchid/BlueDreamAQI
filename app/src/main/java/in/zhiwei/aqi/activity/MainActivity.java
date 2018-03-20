package in.zhiwei.aqi.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import in.zhiwei.aqi.R;
import in.zhiwei.aqi.adapter.NearbyAdapter;
import in.zhiwei.aqi.adapter.SimpleAQIAdapter;
import in.zhiwei.aqi.contract.IAQIContract;
import in.zhiwei.aqi.entity.AQIEntity;
import in.zhiwei.aqi.entity.AQIModel;
import in.zhiwei.aqi.entity.OtherAQIData;
import in.zhiwei.aqi.entity.SimpleAQIBean;
import in.zhiwei.aqi.global.GlobalConstants;
import in.zhiwei.aqi.network.AQIService;
import in.zhiwei.aqi.network.HttpApi;
import in.zhiwei.aqi.presenter.CityAQIPresenter;
import in.zhiwei.aqi.utils.Tools;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 主界面activity，内部嵌套fragment，用于展示城市aqi信息，以及选择城市等
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, IAQIContract.IAQIView, NearbyAdapter.OnNearbyItemClickListener {

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;//toolbar
    @BindView(R.id.srl_main)
    SwipeRefreshLayout mRefreshLayout;//刷新的layout
    @BindView(R.id.ll_aqi_main)
    LinearLayout mLinearLayout;//aqi的主体布局
    @BindViews({R.id.btn_error_main, R.id.iv_airship_main})
    List<View> viewError;//遇到网络错误view控制

    @BindView(R.id.tv_api_num_header)
    TextView tvAQINumHeader;//header 中aqi数字
    @BindView(R.id.tv_api_level_name_header)
    TextView tvAQILevelNameHeader;//header中aqi level
    @BindView(R.id.tv_api_update_time_header)
    TextView tvAQIUpdateTimeHeader;//header中，更新数据的时间
    @BindView(R.id.tv_temperature_api_fragment)
    TextView tvAQITemperature;//温度和风速
    @BindView(R.id.cl_header_api_main)
    ConstraintLayout clAQIHeader;//header布局
    @BindView(R.id.rv_simple_aqi_fragment)
    RecyclerView mRvSimpleAQI;//星期AQI数据的recyclerView
    @BindView(R.id.rv_nearby_city_aqi_fragment)
    RecyclerView mRvNearby;//附近监测站点
    @BindView(R.id.tv_city_time_weather_data)
    TextView tvCityTimeWeatherData;//城区状况，更新时间
    @BindView(R.id.tv_temperature_weather_data)
    TextView tvTemperatureWeatherData;//温度数值
    @BindView(R.id.tv_humidity_weather_data)
    TextView tvHumidityWeatherData;//湿度
    @BindView(R.id.tv_air_pressure_weather_data)
    TextView tvAirPressureWeatherData;//空气压力

    private NearbyAdapter mNearAdapter;//附近站点的adapter
    private SimpleAQIAdapter mSimpleAQIAdapter;//简单AQI数据预报的数据适配器

    private int mTemperature;//温度
    private int mWindSpeed;//风速
    private int mHumidity;//湿度
    private int mAirPressure;//压强

    private CityAQIPresenter mPresenter;//用于控制fragment UI的presenter
    private static final ButterKnife.Action<View> LIST_INVISIBLE = (view, index) -> view.setVisibility(View.INVISIBLE);//控制list里面的view 的不显
    private static final ButterKnife.Action<View> LIST_VISIBLE = (view, index) -> view.setVisibility(View.VISIBLE);//控制list中的view的显

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife init
        ButterKnife.bind(this);
        //toolsBar,使用toolbar，就需要设置app的theme为noActionbar，
        initToolbar();
        //服务责任协议
        boolean isAgree = SPUtils.getInstance().getBoolean(GlobalConstants.SP_KEY_IS_AGREE_TIPS, false);
        if (!isAgree) {
            initTips();
        }
        //initData
        initData();
        //
        mPresenter = new CityAQIPresenter(this);
        String html = SPUtils.getInstance().getString(GlobalConstants.SP_KEY_AQI_SERVER_DATA);
        if (!html.isEmpty()) {
            mPresenter.parserHtml(html);
        }
        //首次进来就开始请求网络
        onRefresh();
        //检查升级
        mPresenter.checkUpgrade(this);
    }

    /**
     * 提示服务协议
     */
    private void initTips() {
        new AlertDialog.Builder(this).setTitle(R.string.str_service_tips)
                .setCancelable(false)
                .setMessage(getString(R.string.str_content_tips))
                .setPositiveButton(getString(R.string.str_ok), (dialog, which) ->
                        SPUtils.getInstance().put(GlobalConstants.SP_KEY_IS_AGREE_TIPS, true))
                .setNegativeButton(getString(R.string.str_cancel), (dialog, which) ->
                        finish())
                .show();
    }

    /**
     * 配置toolBar
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        //获取当前城市的name
        String cityName = SPUtils.getInstance().getString(GlobalConstants.SP_CURRENT_CITY_NAME, getString(R.string.str_beijing));
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher_round);
            actionBar.setTitle(cityName);
        }
    }

    /**
     * initData
     */
    private void initData() {
        //简单的AQI预报的RecyclerView配置
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvSimpleAQI.setLayoutManager(lm);
        mSimpleAQIAdapter = new SimpleAQIAdapter();
        mRvSimpleAQI.setAdapter(mSimpleAQIAdapter);
        //附近监测站点recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvNearby.setLayoutManager(layoutManager);
        mNearAdapter = new NearbyAdapter();
        //注册监听，item点击
        mNearAdapter.setItemListener(this);
        mRvNearby.setAdapter(mNearAdapter);
        //避免和scrollView滑动冲突
        mRvSimpleAQI.setNestedScrollingEnabled(false);
        mRvNearby.setNestedScrollingEnabled(false);
        //refreshlayout
        mRefreshLayout.setOnRefreshListener(this);
    }

    @OnLongClick({R.id.cl_header_api_main})
    public boolean doLongClick(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.pop_aqi_level_description).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return false;
    }

    @OnClick({R.id.btn_error_main})
    public void doClick(View view) {
        onRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location_menu://定位到当前位置
                getLocation();
                break;
//            case R.id.map_menu://查看aqi地图数据
//                String city = SPUtils.getInstance().getString(GlobalConstants.SP_CURRENT_CITY_ID, "beijing");
//                CityAQIMapActivity.actionActivity(this, city);
//                break;
            case R.id.share_menu://分享
                shareIt();
                break;
//            case R.id.setting_menu://设置
//                new AlertDialog.Builder(this)
//                        .setView(R.layout.pop_aqi_level_description)
//                        .setCancelable(true)
//                        .create().show();
//                break;
            case R.id.about_menu://关于App和作者
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 使用ip定位aqi城市，然后刷新
     */
    private void getLocation() {
        HttpApi.getInstance().create(AQIService.class)
                .getNearestStation("")//城市编号，暂时v1.0版本不写
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nearestRes -> {
                    String city = nearestRes.getG().getCity();
                    String cityName;
                    if (Tools.isChinese()) {
                        cityName = nearestRes.getG().getNames().getZhCN();
                    } else {
                        cityName = nearestRes.getG().getNames().getEn();
                    }
                    if (!TextUtils.isEmpty(city)) {
                        //缓存当前城市信息
                        SPUtils.getInstance().put(GlobalConstants.SP_CURRENT_CITY_ID, city);
                        SPUtils.getInstance().put(GlobalConstants.SP_CURRENT_CITY_NAME, cityName);
                    }
                    //刷新
                    onRefresh();
                }, Throwable::printStackTrace);
    }

    /**
     * 分享app给朋友
     */
    private void shareIt() {
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.str_url_app_download));
        startActivity(Intent.createChooser(textIntent, getString(R.string.str_share_title)));
    }

    /**
     * 覆写函数，用于显示出icon和text
     *
     * @param view view
     * @param menu menu对象
     * @return 返回boolean
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() != MenuBuilder.class) {
                return super.onPrepareOptionsPanel(view, menu);
            }
            try {
                @SuppressLint("PrivateApi") Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public void onGetAQISuccess(@NonNull AQIEntity aqiEntity) {
        AQIModel aqiModel = aqiEntity.getAqiModel();
        OtherAQIData otherAQIData = aqiEntity.getOtherAQIData();
        //header 设置
        setHeaderData(aqiModel);
        //温度，风速设置
        setTempWind(aqiModel, otherAQIData.getWindDirection());
        //气象数据
        setWeatherData(aqiModel);
        //附近站点监测数据
        mNearAdapter.setData(aqiModel.getNearest());
        //简单aqi预报list
        List<AQIModel.ForecastBean.AqiBean> aqiBeans = aqiModel.getForecast().getAqi();
        List<SimpleAQIBean> simpleAQiList = Tools.getSimpleAQiList(this, aqiBeans);
        mSimpleAQIAdapter.setData(simpleAQiList);
        //设置toolbar的title
        mToolbar.setTitle(aqiModel.getCity().getName());
        //refreshlayout
        mRefreshLayout.setRefreshing(false);
        //根据网络状态的提示页面显隐控制
        mLinearLayout.setVisibility(View.VISIBLE);
        ButterKnife.apply(viewError, LIST_INVISIBLE);
    }

    /**
     * 设置风速度，温度数据
     *
     * @param aqiModel      aqi对象
     * @param windDirection
     */
    private void setTempWind(@NonNull AQIModel aqiModel, @NonNull String windDirection) {
        List<AQIModel.IaqiBean> iaqi = aqiModel.getIaqi();
        for (AQIModel.IaqiBean bean : iaqi) {
            switch (bean.getP()) {
                case "pm25"://pm2.5

                    break;
                case "pm10"://pm10

                    break;
                case "o3"://臭氧O3

                    break;
                case "no2"://二氧化氮

                    break;
                case "so2"://二氧化硫

                    break;
                case "co"://一氧化碳

                    break;
                case "t"://温度
                    mTemperature = bean.getV().get(0);//6,标识温度的数据位置而v内含有，平均值、最小、最大，0位置标识平均值
                    break;
                case "d"://凝露点

                    break;
                case "p"://大气压强
                    mAirPressure = bean.getV().get(0);//8,标识压强
                    break;
                case "h"://湿度
                    mHumidity = bean.getV().get(0);//9，标识湿度
                    break;
                case "w"://风速
                    mWindSpeed = bean.getV().get(0);//10 标识风速的数据位置
                    break;
                default:
                    break;
            }
        }
        tvAQITemperature.setText(Tools.strFormat(getString(R.string.str_temperature_wind), mTemperature, mWindSpeed, windDirection));//温度和风速
    }

    /**
     * header模块数据
     *
     * @param aqiModel aqi数据对象
     */
    private void setHeaderData(@NonNull AQIModel aqiModel) {
        int mAQI = aqiModel.getAqi();
        tvAQINumHeader.setText(Tools.strFormat("%d", mAQI));//aqi值
        tvAQILevelNameHeader.setText(Tools.getAQILevel(this, mAQI));//污染程度
        //设置字体颜色 aqi > 150 颜色白色，否则，黑色
        int colorBG = mAQI > 150 ? Color.WHITE : Color.BLACK;
        tvAQINumHeader.setTextColor(colorBG);
        tvAQILevelNameHeader.setTextColor(colorBG);
        clAQIHeader.setBackgroundColor(getResources().getColor(Tools.getAQILevelColor(mAQI)));//背景颜色
        int colorBanner = mAQI > 150 ? getResources().getColor(R.color.colorWhiteTranslate) : getResources().getColor(R.color.colorBlackTranslate);
        String updateTime = aqiModel.getTime().getS().getCn().getTime();
        //首要污染物
        String dominentpol = aqiModel.getDominentpol();
        String strBanner = dominentpol == null ? updateTime : Tools.strFormat(getString(R.string.str_api_update_time), updateTime, dominentpol);
        tvAQIUpdateTimeHeader.setText(strBanner);//更新时间
        tvAQIUpdateTimeHeader.setBackgroundColor(colorBanner);
    }

    /**
     * 设置气象数据，板块
     *
     * @param aqiModel aqi的数据对象
     */
    private void setWeatherData(@NonNull AQIModel aqiModel) {
        AQIModel.TimeBean.SBean sBean = aqiModel.getTime().getS();
        //默认就支持中文，英文
        String updateTime;
        if (Tools.isChinese()) {
            updateTime = sBean.getCn().getTime();
        } else {
            updateTime = sBean.getEn().getTime();
        }
        String city = aqiModel.getCity().getName();
        String weekDayTime = updateTime.substring(4, updateTime.length());
        tvCityTimeWeatherData.setText(Tools.strFormat(getString(R.string.str_current_weather_data), city, weekDayTime));
        tvTemperatureWeatherData.setText(Tools.strFormat(getString(R.string.str_temperature_num), mTemperature));
        tvHumidityWeatherData.setText(Tools.strFormat(getString(R.string.str_humidity), mHumidity));
        tvAirPressureWeatherData.setText(Tools.strFormat(getString(R.string.str_air_pressure), mAirPressure));
    }

    @Override
    public void onGetAQIFailed(@NonNull String error) {
        mRefreshLayout.setRefreshing(false);
        //根据网络状态的提示页面显隐控制
        if (SPUtils.getInstance().getString(GlobalConstants.SP_KEY_AQI_SERVER_DATA).isEmpty()) {
            //如果缓存了aqi数据，则显示缓存数据，而不是直接跳转到错误界面,否则显示错误信息界面
            mLinearLayout.setVisibility(View.INVISIBLE);
            ButterKnife.apply(viewError, LIST_VISIBLE);
        }
	    ToastUtils.showShort(Tools.convertError(error));
    }

    @Override
    public void onCheckUpgradeSuccess(boolean hasNewVersion) {
        if (hasNewVersion) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.str_find_new))
                    .setMessage(getString(R.string.str_get_new))
                    .setNegativeButton(getString(R.string.str_cancel), null)
                    .setPositiveButton(getString(R.string.str_ok), (dialog, which) ->
                            mPresenter.downloadApk(MainActivity.this))
                    .show();
        } else {
            ToastUtils.showShort(R.string.str_version);
        }
    }

    @Override
    public void onCheckUpgradeFailed(@NonNull String error) {
        ToastUtils.showShort(error);
    }

    @Override
    public void onNearbyItemClicked(@NonNull String station) {
        mPresenter.changeStation(station);
        //缓存为current station
        SPUtils.getInstance().put(GlobalConstants.SP_CURRENT_CITY_ID, station);
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        //开始网络请求
        mPresenter.start();
        //加载中
        mRefreshLayout.setRefreshing(true);
    }
}
