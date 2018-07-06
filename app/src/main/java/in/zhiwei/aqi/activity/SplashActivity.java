package in.zhiwei.aqi.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.taobao.sophix.SophixManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import in.zhiwei.aqi.R;
import in.zhiwei.aqi.utils.AnTools;
import in.zhiwei.aqi.utils.Tools;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 闪屏界面，splash动画
 * Author: gzw48760.
 * Date: 2018/3/23 0023,10:48.
 */

public class SplashActivity extends AppCompatActivity {
	//lottie views
	@BindViews({R.id.lav_a_splash, R.id.lav_q_splash, R.id.lav_i_splash})
	List<LottieAnimationView> mLottieViews;//lottie 动画
	private static final ButterKnife.Setter<View, BaseKeyframeAnimation.AnimationListener> AA = (v, t, index) -> {
	};
	@BindView(R.id.iv_logo_splash)
	ImageView ivLogo;//logo
	@BindView(R.id.tv_version_splash)
	TextView tvVersion;//版本
	@BindView(R.id.tv_me_splash)
	TextView tvDesc;
	private Disposable disposable;//控制阀

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//statusBar 隐藏
		BarUtils.setStatusBarVisibility(this, false);
		setContentView(R.layout.activity_splash);
		ButterKnife.bind(this);
		//注册动画监听,最后一个view注册监听
		mLottieViews.get(mLottieViews.size() - 1).addAnimatorUpdateListener(animation -> {
			float animatedValue = (float) animation.getAnimatedValue();
			//动画结束，进入主界面
			if (animatedValue == 1.0f) {
				//启动主界面
				disposable = Observable.timer(100, TimeUnit.MILLISECONDS)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(aLong -> {
							Intent intent = new Intent(SplashActivity.this, MainActivity.class);
							startActivity(intent);
							overridePendingTransition(R.anim.main_in, R.anim.main_out);
							//结束当前activity
							finish();
						});
			}
		});
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/stcaiyun.ttf");
		tvVersion.setTypeface(typeface);
		//设置版本信息
		String appVersionName = AppUtils.getAppVersionName();
		String version = Tools.strFormat(getString(R.string.str_version_splash), appVersionName);
		tvVersion.setText(version);
		//底部描述的字体
		typeface = Typeface.createFromAsset(getAssets(), "fonts/curlz.ttf");
		tvDesc.setTypeface(typeface);
		//sophix热修复的请求服务器补丁
		SophixManager.getInstance().queryAndLoadNewPatch();
	}

	/**
	 * 所有的动画开始
	 */
	private void playAnimation() {
		for (LottieAnimationView lottieView : mLottieViews) {
			lottieView.playAnimation();
		}
		AnTools.alphaAnimation(ivLogo);
	}

	/**
	 * 所有的动画 取消
	 */
	private void cancelAnimation() {
		for (LottieAnimationView lottieView : mLottieViews) {
			lottieView.cancelAnimation();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		//开始动画
		playAnimation();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//结束动画
		cancelAnimation();
		if (disposable != null) {
			disposable.dispose();
		}
	}
}
