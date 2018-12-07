package `in`.zhiwei.aqi.activity

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.global.GlobalConstants
import `in`.zhiwei.aqi.utils.Tools
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.blankj.utilcode.util.SPUtils

/**
 * Author: gzw48760.
 * Date: 2018/2/8 0008,16:25.
 */

class CityAQIMapActivity : AppCompatActivity() {

    @BindView(R.id.toolbar_main)
    lateinit var mToolbar: Toolbar//toolbar
    @BindView(R.id.web_aqi_map)
    lateinit var mWebView: WebView
    @BindView(R.id.pb_aqi_map)
    lateinit var mProgressBar: ProgressBar//
    @BindView(R.id.iv_error_map)
    lateinit var ivError: ImageView
    @BindView(R.id.btn_error_map)
    lateinit var btnError: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aqi_map)
        ButterKnife.bind(this)
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        //获取当前城市的name
        val cityName =
            SPUtils.getInstance().getString(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, getString(R.string.str_beijing))
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back)
            actionBar.title = cityName
        }
        // init webView
        initWebView()
        //获取传递来的city参数,必须是小写的拼音才行
        val city = intent.getStringExtra(INTENT_KEY_CITY_ID)
        if (!TextUtils.isEmpty(city)) {
            loadUrl(Tools.strFormat(URL_AQI_CITY_MAP, city.toLowerCase()))
        }
    }

    /**
     * 初始化webView配置
     */
    private fun initWebView() {
        //webView设置
        val webSettings = mWebView.settings
        //启动js,这样才能加载web地图数据
        webSettings.javaScriptEnabled = true
        //启用缓存
        webSettings.setAppCacheEnabled(true)
        webSettings.loadWithOverviewMode = true
        mWebView!!.isVerticalScrollBarEnabled = false
        webSettings.setSupportZoom(true)
        //用于适配内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    /**
     * 加载html文件
     *
     * @param url
     */
    private fun loadUrl(url: String) {
        mWebView.loadUrl(url)//url 为需要加载的html地址
        //设置WebViewClient类
        mWebView.webViewClient = object : WebViewClient() {
            //设置加载前的函数
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                mProgressBar.visibility = View.VISIBLE
            }

            //设置结束加载函数
            override fun onPageFinished(view: WebView, url: String) {
                mProgressBar.visibility = View.INVISIBLE
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                //internet disconnected
                if (error.errorCode == -2) {
                    mWebView.visibility = View.INVISIBLE
                    ivError.visibility = View.VISIBLE
                    btnError.visibility = View.VISIBLE
                }
                Log.e("CityAQIMapActivity", "webView Client onReceived Error : " + error.description)
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                //拦截，重载url请求，使在map中点击aqi点，不会跳转
                return true
            }
        }
    }

    @OnClick(R.id.btn_error_map)
    fun doClick(view: View) {
        mWebView.reload()
        ivError.visibility = View.INVISIBLE
        btnError.visibility = View.INVISIBLE
        mWebView.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val i = item.itemId
        if (i == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.main_in, R.anim.map_scale_out)
    }

    companion object {
        //val声明常量，添加const，可以使得常量给java共享使用，似乎是这么回事
        private const val INTENT_KEY_CITY_ID = "city_id"//用于查看地图数据的城市id，拼音或英文
        private const val URL_AQI_CITY_MAP = "http://aqicn.org/map/%s/m/"//查看城市aqi地图信息,here表示当前城市

        /**
         * 提供外部调用本Activity的便捷方式
         *
         * @param context context
         * @param cityID  城市id 的string
         */
        fun actionActivity(context: Context, cityID: String) {
            val intent = Intent(context, CityAQIMapActivity::class.java)
            intent.putExtra(INTENT_KEY_CITY_ID, cityID)
            context.startActivity(intent)
        }
    }
}
