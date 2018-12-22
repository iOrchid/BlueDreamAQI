package `in`.zhiwei.aqi.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * UI界面相关的工具类
 * Author: zhiwei.
 * Github: https://github.com/zhiwei1990
 * Date: 2018/12/22,00:20.
 * You never know what you can do until you try !
 */
object UITools {
	/**
	 * 全透状态栏
	 */
	fun setStatusBarFullTransparent(activity: Activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
			activity.window.decorView.systemUiVisibility =
					(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
			activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
			activity.window.statusBarColor = Color.TRANSPARENT

		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//		activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
		}
	}

	/**
	 * 半透明状态栏
	 */
	fun setHalfTransparent(activity: Activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			val decorView = activity.window.decorView
			val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
			decorView.systemUiVisibility = option;
			activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19表示4.4
			activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//虚拟键盘也透明
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

}