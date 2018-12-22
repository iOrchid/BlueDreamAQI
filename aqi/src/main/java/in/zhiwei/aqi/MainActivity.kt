package `in`.zhiwei.aqi

import `in`.zhiwei.aqi.ui.main.MainFragment
import `in`.zhiwei.aqi.utils.UITools
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)

		UITools.setStatusBarFullTransparent(this)

		if (savedInstanceState == null) {
			supportFragmentManager.beginTransaction()
				.replace(R.id.container, MainFragment.newInstance())
				.commitNow()
		}
	}

}
