package in.zhiwei.aqi.webView;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
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

import com.blankj.utilcode.util.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.zhiwei.aqi.R;

/**
 * 全局的一个WebView的Activity，用于统一处理web请求的界面
 * Author: gzw48760.
 * Date: 2018/7/12 0012,9:58.
 */
public class WebViewActivity extends AppCompatActivity {

	@BindView(R.id.web_view_activity)
	WebView mWebView;
	@BindView(R.id.pb_web_activity)
	ProgressBar mProgressBar;//
	@BindView(R.id.iv_error_web_activity)
	ImageView ivError;
	@BindView(R.id.btn_error_web_activity)
	Button btnError;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		ButterKnife.bind(this);
		// init webView
		initWebView();
		String url = getIntent().getStringExtra("url");
		if (url != null && !url.isEmpty()) {
			loadUrl(url);
		} else {
			ToastUtils.showShort("web地址为空");
			finish();
		}
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
	 * @param url web的url
	 */
	private void loadUrl(@NonNull String url) {
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
				if (error.getErrorCode() == -2) {
					mWebView.setVisibility(View.INVISIBLE);
					ivError.setVisibility(View.VISIBLE);
					btnError.setVisibility(View.VISIBLE);
				}
			}

		});
	}

	@OnClick(R.id.btn_error_web_activity)
	public void doClick(View view) {
		mWebView.reload();
		ivError.setVisibility(View.INVISIBLE);
		btnError.setVisibility(View.INVISIBLE);
		mWebView.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home://toolbar的返回键
				finish();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void finish() {
		super.finish();
		//转场动画，先不用
//		overridePendingTransition(R.anim.main_in, R.anim.map_scale_out);
	}
}
