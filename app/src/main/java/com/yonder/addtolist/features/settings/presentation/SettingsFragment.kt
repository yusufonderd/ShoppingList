package com.yonder.addtolist.features.settings.presentation

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.databinding.SettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsFragmentBinding>() {

  val viewModel: SettingsViewModel by viewModels()

  override fun initBinding(
    inflater: LayoutInflater
  ): SettingsFragmentBinding {
   return SettingsFragmentBinding.inflate(layoutInflater)
  }

}