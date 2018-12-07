package `in`.zhiwei.aqi.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

/**
 * 动画相关的工具类
 * Author: gzw48760.
 * Date: 2018/4/4 0004,11:09.
 */
object AnTools {

    /**
     * 旋转动画
     *
     * @param view view
     */
    fun rotateAnimation(view: View) {
        val anim = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
        anim.duration = 1000
        anim.start()
    }

    /**
     * 渐变动画
     *
     * @param view view
     */
    fun alphaAnimation(view: View) {
        val anim = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 1.0f)
        anim.duration = 1500
        anim.start()
    }

    /**
     * Y轴缩放动画
     * @param view view
     */
    fun scaleAnimation(view: View) {
        val scaleAnim = ObjectAnimator.ofFloat(view, "scaleY", 0.2f, 0.6f, 1.0f)
        scaleAnim.duration = 1000
        scaleAnim.start()
    }

    /**
     * 组合动画
     *
     * @param view view
     */
    fun setAnimation(view: View) {
        val alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 0.3f, 0.5f, 0.7f, 0.9f, 1.0f)
        val rotateAnim = ObjectAnimator.ofFloat(view, "rotation", 0.0f, 360.0f, 0.0f)
        val set = AnimatorSet()
        set.playTogether(alphaAnim, rotateAnim)
        set.duration = 1000
        set.start()
    }
}
/**
 * 私有构造函数
 */
