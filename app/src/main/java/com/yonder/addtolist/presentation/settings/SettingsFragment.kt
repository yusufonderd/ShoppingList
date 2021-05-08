package com.yonder.addtolist.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yonder.addtolist.R
import com.yonder.addtolist.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {


  val viewModel: SettingsViewModel by viewModels()


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.settings_fragment, container, false)
  }

  override fun setObserver() {
    TODO("Not yet implemented")
  }


}