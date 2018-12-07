package `in`.zhiwei.aqi.webView

import `in`.zhiwei.aqi.R
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.blankj.utilcode.util.ToastUtils

/**
 * 全局的一个WebView的Activity，用于统一处理web请求的界面
 * Author: gzw48760.
 * Date: 2018/7/12 0012,9:58.
 */
class WebViewActivity : AppCompatActivity() {

    @BindView(R.id.web_view_activity)
    lateinit var mWebView: WebView
    @BindView(R.id.pb_web_activity)
    internal lateinit var mProgressBar: ProgressBar//
    @BindView(R.id.iv_error_web_activity)
    internal lateinit var ivError: ImageView
    @BindView(R.id.btn_error_web_activity)
    internal lateinit var btnError: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        ButterKnife.bind(this)
        // init webView
        initWebView()
        val url = intent.getStringExtra("url")
        if (url != null && !url.isEmpty()) {
            loadUrl(url)
        } else {
            ToastUtils.showShort("web地址为空")
            finish()
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
        mWebView.isVerticalScrollBarEnabled = false
        webSettings.setSupportZoom(true)
        //用于适配内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    /**
     * 加载html文件
     *
     * @param url web的url
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
                if (error.errorCode == -2) {
                    mWebView.visibility = View.INVISIBLE
                    ivError.visibility = View.VISIBLE
                    btnError.visibility = View.VISIBLE
                }
            }

        }
    }

    @OnClick(R.id.btn_error_web_activity)
    fun doClick(view: View) {
        mWebView.reload()
        ivError.visibility = View.INVISIBLE
        btnError.visibility = View.INVISIBLE
        mWebView.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home//toolbar的返回键
            -> finish()
            else -> {
                //do nothing
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        //转场动画，先不用
        //		overridePendingTransition(R.anim.main_in, R.anim.map_scale_out);
    }
}
