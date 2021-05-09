package com.yonder.addtolist.presentation.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yonder.addtolist.core.base.BaseFragment
import com.yonder.addtolist.databinding.SettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsFragmentBinding>() {

  val viewModel: SettingsViewModel by viewModels()

  override fun setObserver() {
    TODO("Not yet implemented")
  }

  override fun setupViews() {
    TODO("Not yet implemented")
  }
  override fun initBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): SettingsFragmentBinding {
   return SettingsFragmentBinding.inflate(layoutInflater)
  }


}