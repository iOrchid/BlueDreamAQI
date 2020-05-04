package org.zhiwei.aqi.ui.main

import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.top_main_fragment.*
import org.zhiwei.aqi.BuildConfig
import org.zhiwei.aqi.adapter.StationAdapter
import org.zhiwei.aqi.databinding.MainFragmentBinding
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

	private var locationManager: LocationManager? = null


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
			}
			liveAQI.observeKt {
				stationAdapter.updateList(it.stations)
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
				)
				loadingAnim.interpolator = AnimationUtils.loadInterpolator(
					requireContext(),
					android.R.anim.linear_interpolator
				)
				if (loading) {
					loadingAnim
						.start()
				} else {
					loadingAnim.end()
				}
			}
		}

		locationManager = requireContext().getSystemService<LocationManager>()
		val criteria = Criteria()
		criteria.isAltitudeRequired = false//是否需要海拔信息，若是，则使用gps定位
		val bestProvider = locationManager?.getBestProvider(criteria, false)
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
		locationManager?.requestLocationUpdates(bestProvider, 1000L, 1000f, listener)
		LogKt.i("onViewCreated 131: bestProvider $bestProvider")

	}

	/**
	 * local listener
	 */
	private val listener = object : LocationListener {
		override fun onLocationChanged(location: Location?) {
			val accuracy = location?.accuracy//获取精确位置
			val altitude = location?.altitude//获取海拔
			val latitude = location?.latitude ?: 0.0//获取纬度，平行
			val longitude = location?.longitude ?: 0.0//获取经度，垂直

			val geocoder = Geocoder(requireContext())
			val fromLocation = geocoder.getFromLocation(latitude, longitude, 10)
			viewModel.liveCity.postValue(fromLocation[0].locality)
			LogKt.d("onLocationChanged 151: location ${fromLocation.joinToString()}")
		}

		override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
			LogKt.d("onStatusChanged 155: $provider $status")
		}

		override fun onProviderEnabled(provider: String?) {
			LogKt.d("onProviderEnabled 159: $provider")
		}

		override fun onProviderDisabled(provider: String?) {
			LogKt.d("onProviderDisabled 163: $provider")
		}

	}

	override fun onDestroy() {
		mBinding = null
		locationManager?.removeUpdates(listener)
		super.onDestroy()
	}
}