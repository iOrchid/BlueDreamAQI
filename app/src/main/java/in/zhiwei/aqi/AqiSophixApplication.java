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

	private final String APP_ID = "24963602-1";//hotfix 申请的appid

	private final String APP_SECRET = "ea7d507c59eaca30bf082980bd30d44d";//hotfix的app secret

	private final String APP_RSA_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCq02vCD15b3BR9tnD+/S2CkHf4aF+8r8hSKM95B0zu6qu2WvtKSWRN5cvYH2Z9zdy0TdXA6G2X7runin7RbZJW0wZJqWyXR4O3xZ57m0INzv6KOEkd+n0mhOTVkdwvRNEOwX38ChoES+Nt8Ls2NoueFmXziUkFOEMsLsewA0SoqbvLqFjINEhTFvauxHpxGoWTv2G9r/Tn86BP8Xd7zD8xd0yHtnTYKIyYF5qdJBNeLLzwrrqyUrRY/z2d3cibM32hp3aSL1af8PdAb3xXOad3KfZ1yMjxucrj9CqpQrX4phF9kHA6nxVPYJH3lwFkzsTzrirFKuqlsAz8cvOZiTKhAgMBAAECggEAbZW3WQR26cLjOkohbtKHsWxwKeD7GZZCkcIwZlmylsngYdk3v04ZlIdIH1r1atr3LIuKBniUZZT2uOH1vO/not8492RCj0GHVAroQi81TpptW8oDzcF1K5KxIfiyIXiqDCdAZMEuM3djbJGz53FSW5XOzU5Sk5CmNt/DpjxSfUdlaWfM2i3qHF0+6t6ZCzmDeEEgR7/Yc0xdKjFnfbBQZILiEeyZM00GDwh9J9CNFeXwr/ACp0LDZcmL0lC0FKMpYyKcn2Hb1byWInbBWgb/ps8l0gWb1v48oC3hiOMpGlxWO+Jd2mWqk5wUQIv6TUBRdOTbtVnMtrIikhoz4YnB4QKBgQD3NlYn43V9WhJqgX1mccjBiqWKJ2Ng8J4LIppTXcmmPoqahAMz97sTLzVDp3Do/7oyRzHV1FXNRBMdAXOLBMBuEQAGE8CN0xj1cQFewTcsrKYbd9ZLl0idHRRN0XYloFU+dyucAN6EJxTlO5sLSRxy3gD+EuTVe04h74b58b2GxQKBgQCw5fVXkmpSijL838clR4iG/9Ri1BQw9jiblFpoI3ebJe6EwUaUYkVythdZxWcLsGawSBTjQ5hRbnOTqQQvHJkX7gNNQxjfCkr7sLxDy/UZACGY21DMmyH8VJcZXUsYrxbpsxpS4y37rFdE8nG1Sa2UN7Z/33VPSiBLNXiGae6aLQKBgG/djq8OJqRKlfrMDcrFwmqSKsaRfE7pkxrG1b7TueQYeO/ivPBPMcBEpH2wz08gKUmHHe3837a7D0sY1QgbZlhoKPKXDZMf6zNlp5ERLb5xgdj8KWSbWKh/+CgemK/+FZTmqD03x3lVlgcFKeu5cb4o55oRQqu32GtS0GlaEsFhAoGBAKbKK2lGQ7lPVIPY+ovLxo6C6F3GWMNRZE5MsnE9/6fpO9sv4LxHa/OcfKfH2EDNMgVD8W3WbedZnN+8nJVPAYnugG54tRqXOdvV56yEwbOwy/yzzJseKg0gNNdVf1jmvVPb6xX73X9OH3qCnXOw5Fi7rvciWhlIaup+oAUcHDyBAoGBAJL6c8O6PRerQd/BqAr2GvUdTbdixsAmTppH+HYRSx5cgvkK3qDCeEpgAls+i5mH/uTGJ2hduZMhzCEr5hylBv1DYJLO1gOSADKfdff8gtUZDVFi/hCJqDHZbw5ZJ64GB7s/xhqK/z7vETNTv1cKUjxz5Cxd0a/suAU47i/3+zKx";//hotfix的rsa key

	// 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
	@Keep
	@SophixEntry(AQIApplication.class)
	static class RealApplicationStub {
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//如果需要使用MultiDex，需要在此处调用。MultiDex.install(this);
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
				.setSecretMetaData(APP_ID, APP_SECRET, APP_RSA_KEY)
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
