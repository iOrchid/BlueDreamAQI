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
	private final String APP_ID = "24945841-1";//hotfix 申请的appid
	private final String APP_SECRET = "1d1ed7fffced3e8d157343a80e315814";//hotfix的app secret
	private final String APP_RSA_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDGOcDNHSbEXH7qE8HpckJEvVgbN6fhhX5TpbuYzEYVrUCvn+4+fkoNIH9t+xhADfNQBaR2T2j9hdVn4AZwppKkCuYiWFjQLYmKKz8xmVVUnFtmPi1aFttViBGfHS173oJUenIIWEPiWaGfgZ1S+JGLkWeVoNPzrUTuKiMUaUBbjW9/aktZ5VG0sbjbxr7w3M61Oyk4Cv8xpl6rxev0wky7nDitFg+q6fDV/smfRnY+LBPsV9WnuaA+Mxi1fakEkJdeeOF9GmzdtZZVUdejsvHYN5RlXjr0aQDj4yWFtct8CaKIIwF55xXWxVkdvrCr6hkhbRQoUAWmIyoxTUS2doojAgMBAAECggEABKYcGHNjkl1c+J5oANKWLUiHhXPjsw1PbiTH5oh76Ew85SGKE9sgX3hwpKKr6eyp1Smcts2b974oyBvf6sptS/2ZchrCUneO7zeebspazvYvypEQvTkmAbaEO0gS5gHM6rBIdlujk+5oNPZ0OxrrRuutEzxHXEKnCDLiybrCMKNw5n0CBqnovQVK68jtvMZHpuyHWX57JqqWAzgibd1cUWkbrGkQCmd2zrftutGNv3V5ImGAskMR3yEOwzSwXSYC1xVbZSUHpb2MjwAIgGXbDc7wLZ8RtiEHQH5YK2WCLXKs4ZQgGC1Zzvwmil/uXZPuMFcjf0dQm70Q63O76XyckQKBgQD6uL2XLcwxI1pmw7ltVVDHYKzaytiM5+MeZaFp8EcG81zY/BGMVZPg+K/Tl/n/jg+ckZwzir9ynGhmZmbJZYY1HpCxtxIHmvVYbbJyyEW5XWxHSFn5a4Uhj/R7UqEKWeZIAoYV8WjpPGEET+P8WB1xsWQw9GAUA7v+o6Rsv8Z7awKBgQDKZhYMq1wEQ7hEbFsDtTGu2prrUhOGlWyHF/tCjxfOkIvPXZ2r5VMx+oAFZNtxWyEylxZlBoflpU0hMV3mJOQTyPOZdFk1/xh3xouoEyoFvEC6HaRDVXeLy5aclby3UEya8ArnJvttq+MpPrPF5dJbG95GkZMF8hJy66fzPg3SKQKBgDtgvz3rkb1dcw59cg/LscrWQXm7qpeMX4SWayjsx9WEk0usPveuWMxh+ToydvmoClh5P7YRORAKrMr7m4I88hDogTolcjas40gjCq0WczTYREmJgA2LAkeVkUAXrJ4H9nq9ZkYSG1eJfiyIQyVDNQ31BhZ1+b8jt0UyOkGrFXWrAoGAZ3Yp3U4XWoK3hhqRp+KOxCAxQwuQuaJWePRUV2DIPap8HYNwXvd6QLkZiihVWKvJ24+KPhhJjaWjOM2Af23qPQbjJ1VnaQe+nTOcHk21lHr352vRlS3yTz7B/cc5Uce1cRo1qJWvvw83rtTDluz1S+eCBzbRHh/xOFeoYdodvIkCgYAc1EGZHTWoixrl202blmWb/32/LrY1TwQcw6rv0h9HNhTe/GL3FDQZAUzhXxzMg8TPGZN5hbLEjiW2r3Q6/zaD9h+pLTaOdWEWGlIbmMrUANenKdPac7YXNMHRf/SadCDtVCYKRhM320RK8dnXx4XaeWOu31nS19et+vyIQNowcw==";//hotfix的rsa key

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
