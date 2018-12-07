package `in`.zhiwei.aqi.activity

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.utils.AnTools
import `in`.zhiwei.aqi.utils.Tools
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.BindViews
import butterknife.ButterKnife
import com.airbnb.lottie.LottieAnimationView
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.BarUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 闪屏界面，splash动画
 * Author: gzw48760.
 * Date: 2018/3/23 0023,10:48.
 */

class SplashActivity : AppCompatActivity() {
    //lottie views
    @BindViews(R.id.lav_a_splash, R.id.lav_q_splash, R.id.lav_i_splash)
    lateinit var mLottieViews: Array<LottieAnimationView>//lottie 动画
    @BindView(R.id.iv_logo_splash)
    lateinit var ivLogo: ImageView//logo
    @BindView(R.id.tv_version_splash)
    lateinit var tvVersion: TextView//版本
    @BindView(R.id.tv_me_splash)
    lateinit var tvDesc: TextView

    private var disposable: Disposable? = null//控制阀

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //statusBar 隐藏
        BarUtils.setStatusBarVisibility(this, false)
        setContentView(R.layout.activity_splash)
        ButterKnife.bind(this)
        //注册动画监听,最后一个view注册监听
        mLottieViews[mLottieViews.size - 1].addAnimatorUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            //动画结束，进入主界面
            if (animatedValue == 0.97524375f) {
                //启动主界面
                disposable = Observable.timer(100, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { aLong ->
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.main_in, R.anim.main_out)
                        //结束当前activity
                        finish()
                    }
            }
        }
        var typeface = Typeface.createFromAsset(assets, "fonts/stcaiyun.ttf")
        tvVersion.typeface = typeface
        //设置版本信息
        val appVersionName = AppUtils.getAppVersionName()
        val version = Tools.strFormat(getString(R.string.str_version_splash), appVersionName)
        tvVersion.text = version
        //底部描述的字体
        typeface = Typeface.createFromAsset(assets, "fonts/curlz.ttf")
        tvDesc.typeface = typeface
    }

    /**
     * 所有的动画开始
     */
    private fun playAnimation() {
        for (lottieView in mLottieViews) {
            lottieView.playAnimation()
        }
        AnTools.alphaAnimation(ivLogo)
    }

    /**
     * 所有的动画 取消
     */
    private fun cancelAnimation() {
        for (lottieView in mLottieViews) {
            lottieView.cancelAnimation()
        }
    }

    override fun onResume() {
        super.onResume()
        //开始动画
        playAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        //结束动画
        cancelAnimation()
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}
