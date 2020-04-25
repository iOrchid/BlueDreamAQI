package org.zhiwei.aqi.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.zhiwei.aqi.R

class MainFragment : Fragment() {

	companion object {
		fun newInstance() = MainFragment()
	}

	private val viewModel: MainViewModel by viewModels<MainViewModel> {
		defaultViewModelProviderFactory
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.main_fragment, container, false)
	}
}
