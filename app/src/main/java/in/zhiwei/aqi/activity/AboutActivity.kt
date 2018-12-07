package `in`.zhiwei.aqi.activity

import `in`.zhiwei.aqi.R
import `in`.zhiwei.aqi.utils.Tools
import `in`.zhiwei.aqi.webView.WebViewActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.BarUtils

/**
 * 关于App及开发者
 * Author: gzw48760.
 * Date: 2018/2/27 0027,16:12.
 */

class AboutActivity : AppCompatActivity() {

    @BindView(R.id.cl_bug_report_about)
    lateinit var clBugReport: ConstraintLayout//报告bug的item
    @BindView(R.id.cl_github_about)
    lateinit var clGithub: ConstraintLayout//fork在github上
    @BindView(R.id.cl_wechat_about)
    lateinit var clWechat: ConstraintLayout//微信公众号
    @BindView(R.id.cl_donate_about)
    lateinit var clDonate: ConstraintLayout//捐赠支持
    @BindView(R.id.cl_content_about)
    lateinit var clContent: ConstraintLayout
    @BindView(R.id.tv_version_about)
    lateinit var tvVersion: TextView//当前版本号
    @BindView(R.id.nsv_about)
    lateinit var mScrollView: NestedScrollView
    @BindView(R.id.iv_scenery_about)
    lateinit var ivScenery: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //状态栏 透明
        BarUtils.setStatusBarAlpha(this)
        setContentView(R.layout.activity_about)
        ButterKnife.bind(this)
        //设置版本号
        val appVersionName = AppUtils.getAppVersionName()
        tvVersion.text = Tools.strFormat(getString(R.string.str_version), appVersionName)
    }

    /**
     * 点击事件
     *
     * @param view 需要点击的view
     */
    @OnClick(R.id.cl_bug_report_about, R.id.cl_github_about, R.id.cl_wechat_about, R.id.cl_donate_about)
    fun doClick(view: View) {
        when (view.id) {
            R.id.cl_bug_report_about//Bug 报告
            -> openWeb(getString(R.string.str_url_bug_report))
            R.id.cl_github_about//github上关注
            -> openWeb(getString(R.string.str_url_github))
            R.id.cl_wechat_about//关注微信公众号
            -> showWechatCode()
            R.id.cl_donate_about//捐赠支持
            -> openWeb(getString(R.string.str_url_github))
            else -> {
                //do nothing
            }
        }
    }

    /**
     * 跳转到指定的网页
     */
    private fun openWeb(url: String) {
        val openWeb = Intent(this, WebViewActivity::class.java)
        openWeb.putExtra("url", url)
        startActivity(openWeb)
    }

    /**
     * 展示微信公众号二维码
     */
    private fun showWechatCode() {
        val ivWeChat = ImageView(this)
        ivWeChat.setImageResource(R.drawable.qrcode_wechat)
        AlertDialog.Builder(this)
            .setTitle(R.string.str_qrcode_wechat)
            .setView(ivWeChat)
            .show()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.main_in, R.anim.about_scale_out)
    }
}
