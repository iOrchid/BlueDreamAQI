package in.zhiwei.aqi.receiver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;

import java.io.File;

import androidx.core.content.FileProvider;
import in.zhiwei.aqi.global.GlobalConstants;

/**
 * 下载完成的广播监听
 * Author: gzw48760.
 * Date: 2017/10/11 0011,16:22.
 */

public class DownloadReceiver extends BroadcastReceiver {
    //通过downLoadId查询下载的apk，解决6.0以后安装的问题
    public static File queryDownloadedApk(Context context) {
        File targetApkFile = null;
        DownloadManager downloader = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = SPUtils.getInstance().getLong(GlobalConstants.SP_KEY_DOWNLOAD_ID);
        if (downloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloader != null ? downloader.query(query) : null;
            if (cur != null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;
    }

    @SuppressLint("NewApi")
    public void onReceive(Context context, Intent intent) {
        if (context == null) {
            return;
        }
        long downLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        long cacheDownLoadId = SPUtils.getInstance().getLong(GlobalConstants.SP_KEY_DOWNLOAD_ID);
        if (cacheDownLoadId == downLoadId) {
            install(context);
        }
    }

    /**
     * 安装apk
     *
     * @param context context上下文
     */
    private void install(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File apkFile = queryDownloadedApk(context);
        if (Build.VERSION.SDK_INT >= 24) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件,7.0一下不用添加这个，否则还会报错呢
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(getUriForFile(context, apkFile), "application/vnd.android.package-archive");
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * Android N对访问文件权限收回，按照Android N的要求，若要在应用间共享文件，您应发送一项 content://URI，并授予 URI 临时访问权限。
     * 而进行此授权的最简单方式是使用 FileProvider类
     *
     * @param context 上下文
     * @param file    文件file
     * @return 返回URI
     */
    private Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            //这里面的authority一定要和AndroidManifest中FileProvider配置的authority一致
            uri = FileProvider.getUriForFile(context.getApplicationContext(),
                    AppUtils.getAppPackageName() + ".provider",
                    file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
