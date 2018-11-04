package in.zhiwei.aqi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.zhiwei.aqi.R;
import in.zhiwei.aqi.utils.Tools;
import in.zhiwei.aqi.webView.WebViewActivity;

/**
 * 关于App及开发者
 * Author: gzw48760.
 * Date: 2018/2/27 0027,16:12.
 */

public class AboutActivity extends AppCompatActivity {

	@BindView(R.id.cl_bug_report_about)
	ConstraintLayout clBugReport;//报告bug的item
	@BindView(R.id.cl_github_about)
	ConstraintLayout clGithub;//fork在github上
	@BindView(R.id.cl_wechat_about)
	ConstraintLayout clWechat;//微信公众号
	@BindView(R.id.cl_donate_about)
	ConstraintLayout clDonate;//捐赠支持
	@BindView(R.id.cl_content_about)
	ConstraintLayout clContent;
	@BindView(R.id.tv_version_about)
	TextView tvVersion;//当前版本号
	@BindView(R.id.nsv_about)
	NestedScrollView mScrollView;
	@BindView(R.id.iv_scenery_about)
	ImageView ivScenery;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//状态栏 透明
		BarUtils.setStatusBarAlpha(this);
		setContentView(R.layout.activity_about);
		ButterKnife.bind(this);
		//设置版本号
		String appVersionName = AppUtils.getAppVersionName();
		tvVersion.setText(Tools.strFormat(getString(R.string.str_version), appVersionName));
	}

	/**
	 * 点击事件
	 *
	 * @param view 需要点击的view
	 */
	@OnClick({R.id.cl_bug_report_about, R.id.cl_github_about, R.id.cl_wechat_about, R.id.cl_donate_about})
	public void doClick(View view) {
		switch (view.getId()) {
			case R.id.cl_bug_report_about://Bug 报告
				openWeb(getString(R.string.str_url_bug_report));
				break;
			case R.id.cl_github_about://github上关注
				openWeb(getString(R.string.str_url_github));
				break;
			case R.id.cl_wechat_about://关注微信公众号
				showWechatCode();
				break;
			case R.id.cl_donate_about://捐赠支持
				openWeb(getString(R.string.str_url_github));
				break;
			default:
				break;
		}
	}

	/**
	 * 跳转到指定的网页
	 */
	private void openWeb(@NonNull String url) {
//		Uri uri = Uri.parse(url);
//		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		if (intent.resolveActivity(getPackageManager()) != null) {
//			startActivity(intent);
//		} else {
//			ToastUtils.showShort(R.string.str_tips_no_browser);
//		}
		Intent openWeb = new Intent(this, WebViewActivity.class);
		openWeb.putExtra("url", url);
		startActivity(openWeb);
	}

	/**
	 * 展示微信公众号二维码
	 */
	private void showWechatCode() {
		ImageView ivWeChat = new ImageView(this);
		ivWeChat.setImageResource(R.drawable.qrcode_wechat);
		new AlertDialog.Builder(this)
				.setTitle(R.string.str_qrcode_wechat)
				.setView(ivWeChat)
				.show();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.main_in, R.anim.about_scale_out);
	}
}
