package `in`.zhiwei.aqi.receiver

import `in`.zhiwei.aqi.global.GlobalConstants
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import java.io.File

/**
 * 下载完成的广播监听
 * Author: gzw48760.
 * Date: 2017/10/11 0011,16:22.
 */

class DownloadReceiver : BroadcastReceiver() {

    @SuppressLint("NewApi")
    override fun onReceive(context: Context?, intent: Intent) {
        if (context == null) {
            return
        }
        val downLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        val cacheDownLoadId = SPUtils.getInstance().getLong(GlobalConstants.SP_KEY_DOWNLOAD_ID)
        if (cacheDownLoadId == downLoadId) {
            install(context)
        }
    }

    /**
     * 安装apk
     *
     * @param context context上下文
     */
    private fun install(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        val apkFile = queryDownloadedApk(context)
        if (Build.VERSION.SDK_INT >= 24) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件,7.0一下不用添加这个，否则还会报错呢
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.setDataAndType(getUriForFile(context, apkFile), "application/vnd.android.package-archive")
        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    /**
     * Android N对访问文件权限收回，按照Android N的要求，若要在应用间共享文件，您应发送一项 content://URI，并授予 URI 临时访问权限。
     * 而进行此授权的最简单方式是使用 FileProvider类
     *
     * @param context 上下文
     * @param file    文件file
     * @return 返回URI
     */
    private fun getUriForFile(context: Context?, file: File?): Uri {
        if (context == null || file == null) {
            throw NullPointerException()
        }
        val uri: Uri
        if (Build.VERSION.SDK_INT >= 24) {
            //这里面的authority一定要和AndroidManifest中FileProvider配置的authority一致
            uri = FileProvider.getUriForFile(
                context.applicationContext,
                AppUtils.getAppPackageName() + ".provider",
                file
            )
        } else {
            uri = Uri.fromFile(file)
        }
        return uri
    }

    companion object {
        //通过downLoadId查询下载的apk，解决6.0以后安装的问题
        fun queryDownloadedApk(context: Context): File? {
            var targetApkFile: File? = null
            val downloader = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = SPUtils.getInstance().getLong(GlobalConstants.SP_KEY_DOWNLOAD_ID)
            if (downloadId != -1L) {
                val query = DownloadManager.Query()
                query.setFilterById(downloadId)
                query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL)
                val cur = downloader?.query(query)
                if (cur != null) {
                    if (cur.moveToFirst()) {
                        val uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                        if (!TextUtils.isEmpty(uriString)) {
                            targetApkFile = File(Uri.parse(uriString).path)
                        }
                    }
                    cur.close()
                }
            }
            return targetApkFile
        }
    }
}
