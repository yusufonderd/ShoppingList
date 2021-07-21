package com.yonder.addtolist.scenes.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.databinding.SettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsFragmentBinding>() {

  val viewModel: SettingsViewModel by viewModels()

  override fun initBinding(
    inflater: LayoutInflater
  ) = SettingsFragmentBinding.inflate(layoutInflater)


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
  }

  private fun initViews() {
    binding.yoAccount.onClickLayout = {
      findNavController().navigate(SettingsFragmentDirections.actionSettingsToAccountDetail())
    }
  }

}