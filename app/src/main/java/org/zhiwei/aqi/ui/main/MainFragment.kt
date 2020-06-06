package org.zhiwei.aqi.ui.main

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import kotlinx.android.synthetic.main.top_main_fragment.*
import org.zhiwei.aqi.BuildConfig
import org.zhiwei.aqi.adapter.StationAdapter
import org.zhiwei.aqi.databinding.MainFragmentBinding
import org.zhiwei.aqi.utils.PinyinUtils
import org.zhiwei.booster.KtFragment
import org.zhiwei.libcore.LogKt

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月25日 22:00
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 */
class MainFragment : KtFragment() {

	private val viewModel: MainViewModel by viewModels<MainViewModel> {
		defaultViewModelProviderFactory
	}
	private var mBinding: MainFragmentBinding? = null

	private val stationAdapter = StationAdapter()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		if (mBinding == null) {
			mBinding = MainFragmentBinding.inflate(inflater, container, false)
		}
		return mBinding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mBinding?.apply {
			vm = viewModel
			lifecycleOwner = viewLifecycleOwner
			adapter = stationAdapter
			//motionLayout的showPaths
			val mode =
				if (BuildConfig.DEBUG) MotionLayout.DEBUG_SHOW_PATH else MotionLayout.DEBUG_SHOW_NONE
			motionBaseMain.setDebugMode(mode)
			motion_top_main_fragment.setDebugMode(mode)
		}
		viewModel.apply {
			//请求网络
			nearCity()
			//观察结果
			liveCity.observeKt { city: String? ->
				city ?: return@observeKt
				//查询城市的aqi
				pm25Server(city)
				LogKt.d("onViewCreated 80: city $city")
			}
			liveAQI.observeKt {
				stationAdapter.updateList(it.stations)
				aqi_bar_main.setAqiNum(it.aqi)
			}
			isLoading.observeKt { loading ->
				val loadingAnim = ObjectAnimator.ofFloat(
					iv_loading_top_main,
					"rotation",
					0f,
					90f,
					180f,
					270f,
					360f,
					450f,
					540f,
					630f,
					720f
				).apply {
					interpolator = AnimationUtils.loadInterpolator(
						requireContext(),
						android.R.anim.linear_interpolator
					)
					repeatMode = ValueAnimator.REVERSE
				}
				if (loading) {
					loadingAnim
						.start()
				} else {
					loadingAnim.end()
				}
			}
		}

		if (ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_COARSE_LOCATION
			) != PackageManager.PERMISSION_GRANTED
		) {
			LogKt.w("onViewCreated 127: no permission,")
			return
		}

		val amapClient = AMapLocationClient(requireContext())
		val clientOptions = AMapLocationClientOption().apply {
			isOnceLocation = true//单次定位
			isOnceLocationLatest = true//3s内最新位置
			locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
			locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
		}
		amapClient.setLocationOption(clientOptions)

		amapClient.setLocationListener { amapLocation: AMapLocation? ->
			val city = amapLocation?.city ?: "北京"
			val ccs = if (city.endsWith("市")) city.substringBeforeLast("市") else city
			viewModel.liveCity.postValue(PinyinUtils.ccs2Pinyin(ccs))
			LogKt.d("onViewCreated 131: location $ccs")
		}

		amapClient.startLocation()
	}

	override fun onDestroy() {
		mBinding = null
		super.onDestroy()
	}
}