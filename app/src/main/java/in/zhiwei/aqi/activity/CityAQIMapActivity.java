package in.zhiwei.aqi.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.zhiwei.aqi.R;
import in.zhiwei.aqi.global.GlobalConstants;
import in.zhiwei.aqi.utils.Tools;

/**
 * Author: gzw48760.
 * Date: 2018/2/8 0008,16:25.
 */

public class CityAQIMapActivity extends AppCompatActivity {

	private static final String INTENT_KEY_CITY_ID = "city_id";//用于查看地图数据的城市id，拼音或英文
	private static final String URL_AQI_CITY_MAP = "http://aqicn.org/map/%s/m/";//查看城市aqi地图信息,here表示当前城市

	@BindView(R.id.toolbar_main)
	Toolbar mToolbar;//toolbar
	@BindView(R.id.web_aqi_map)
	WebView mWebView;
	@BindView(R.id.pb_aqi_map)
	ProgressBar mProgressBar;//
	@BindView(R.id.iv_error_map)
	ImageView ivError;
	@BindView(R.id.btn_error_map)
	Button btnError;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aqi_map);
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		ActionBar actionBar = getSupportActionBar();
		//获取当前城市的name
		String cityName = SPUtils.getInstance().getString(GlobalConstants.SP_KEY_CURRENT_CITY_NAME, getString(R.string.str_beijing));
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeAsUpIndicator(R.drawable.back);
			actionBar.setTitle(cityName);
		}
		// init webView
		initWebView();
		//获取传递来的city参数,必须是小写的拼音才行
		String city = getIntent().getStringExtra(INTENT_KEY_CITY_ID);
		if (!TextUtils.isEmpty(city)) {
			loadUrl(Tools.strFormat(URL_AQI_CITY_MAP, city.toLowerCase()));
		}
	}

	/**
	 * 提供外部调用本Activity的便捷方式
	 *
	 * @param context context
	 * @param cityID  城市id 的string
	 */
	public static void actionActivity(@NonNull Context context, @NonNull String cityID) {
		Intent intent = new Intent(context, CityAQIMapActivity.class);
		intent.putExtra(INTENT_KEY_CITY_ID, cityID);
		context.startActivity(intent);
	}

	/**
	 * 初始化webView配置
	 */
	private void initWebView() {
		//webView设置
		WebSettings webSettings = mWebView.getSettings();
		//启动js,这样才能加载web地图数据
		webSettings.setJavaScriptEnabled(true);
		//启用缓存
		webSettings.setAppCacheEnabled(true);
		webSettings.setLoadWithOverviewMode(true);
		mWebView.setVerticalScrollBarEnabled(false);
		webSettings.setSupportZoom(true);
		//用于适配内容
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
	}

	/**
	 * 加载html文件
	 *
	 * @param url
	 */
	private void loadUrl(String url) {
		mWebView.loadUrl(url);//url 为需要加载的html地址
		//设置WebViewClient类
		mWebView.setWebViewClient(new WebViewClient() {
			//设置加载前的函数
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				mProgressBar.setVisibility(View.VISIBLE);
			}

			//设置结束加载函数
			@Override
			public void onPageFinished(WebView view, String url) {
				mProgressBar.setVisibility(View.INVISIBLE);
			}

			@TargetApi(Build.VERSION_CODES.M)
			@Override
			public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				super.onReceivedError(view, request, error);
				//internet disconnected
				if (error.getErrorCode() == -2) {
					mWebView.setVisibility(View.INVISIBLE);
					ivError.setVisibility(View.VISIBLE);
					btnError.setVisibility(View.VISIBLE);
				}
				Log.e("CityAQIMapActivity", "webView Client onReceived Error : " + error.getDescription());
			}

			@TargetApi(Build.VERSION_CODES.LOLLIPOP)
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				//拦截，重载url请求，使在map中点击aqi点，不会跳转
				return true;
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int i = item.getItemId();
		if (i == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.main_in, R.anim.map_scale_out);
	}
}
