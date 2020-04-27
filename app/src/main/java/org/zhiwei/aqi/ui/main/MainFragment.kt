package org.zhiwei.aqi.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import org.zhiwei.aqi.adapter.StationAdapter
import org.zhiwei.aqi.databinding.MainFragmentBinding
import org.zhiwei.booster.KtFragment

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
		}
		viewModel.apply {
			//请求网络
			pm25Server()
			//观察结果
			liveAQI.observeKt {
				stationAdapter.updateList(it.stations)
			}
		}

	}

	override fun onDestroy() {
		mBinding = null
		super.onDestroy()
	}
}
