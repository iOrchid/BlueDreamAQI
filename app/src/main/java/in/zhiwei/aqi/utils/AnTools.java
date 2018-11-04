package in.zhiwei.aqi.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * 动画相关的工具类
 * Author: gzw48760.
 * Date: 2018/4/4 0004,11:09.
 */
public class AnTools {
	/**
	 * 私有构造函数
	 */
	private AnTools() {
	}

	/**
	 * 旋转动画
	 *
	 * @param view view
	 */
	public static void rotateAnimation(@NonNull View view) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
		anim.setDuration(1000);
		anim.start();
	}

	/**
	 * 渐变动画
	 *
	 * @param view view
	 */
	public static void alphaAnimation(@NonNull View view) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 1.0f);
		anim.setDuration(1500);
		anim.start();
	}

	/**
	 * Y轴缩放动画
	 * @param view view
	 */
	public static void scaleAnimation(@NonNull View view) {
		ObjectAnimator scaleAnim = ObjectAnimator.ofFloat(view, "scaleY", 0.2f, 0.6f, 1.0f);
		scaleAnim.setDuration(1000);
		scaleAnim.start();
	}

	/**
	 * 组合动画
	 *
	 * @param view view
	 */
	public static void setAnimation(@NonNull View view) {
		ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 0.3f, 0.5f, 0.7f, 0.9f, 1.0f);
		ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(view, "rotation", 0.0f, 360.0f, 0.0f);
		AnimatorSet set = new AnimatorSet();
		set.playTogether(alphaAnim, rotateAnim);
		set.setDuration(1000);
		set.start();
	}
}
