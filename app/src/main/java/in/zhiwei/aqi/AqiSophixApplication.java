package in.zhiwei.aqi;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */

/**
 * Author: gzw48760.
 * Date: 2018/6/28 0028,13:46.
 */
public class AqiSophixApplication extends SophixApplication {
	private final String TAG = "SophixStubApplication";

	// 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
	@Keep
	@SophixEntry(AQIApplication.class)
	static class RealApplicationStub {
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
		initSophix();
	}

	/**
	 * sophix热修复的配置
	 */
	private void initSophix() {
		String appVersion = "0.0.0";
		try {
			appVersion = this.getPackageManager()
					.getPackageInfo(this.getPackageName(), 0)
					.versionName;
		} catch (Exception e) {
		}
		final SophixManager instance = SophixManager.getInstance();
		instance.setContext(this)
				.setAppVersion(appVersion)
				.setAesKey(null)
				.setEnableDebug(true)
				.setEnableFullLog()
				.setPatchLoadStatusStub((mode, code, info, handlePatchVersion) -> {
					if (code == PatchStatus.CODE_LOAD_SUCCESS) {
						Log.i(TAG, "sophix load patch success!");
					} else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
						// 如果需要在后台重启，建议此处用SharePreference保存状态。
						Log.i(TAG, "sophix preload patch success. restart app to make effect.");
					}
				}).initialize();
	}
}
