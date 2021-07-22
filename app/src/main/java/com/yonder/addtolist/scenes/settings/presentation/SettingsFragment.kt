package com.yonder.addtolist.scenes.settings.presentation

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.databinding.SettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsFragmentBinding>() {

  val viewModel: SettingsViewModel by viewModels()

  override fun initBinding(
    inflater: LayoutInflater
  ) = SettingsFragmentBinding.inflate(layoutInflater)

  override fun initViews() {
    binding.yoAccount.onClickLayout = {
      findNavController().navigate(SettingsFragmentDirections.actionSettingsToAccountDetail())
    }
  }

  override fun initObservers() = Unit

}