package com.yonder.addtolist.features.splash.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.core.base.BaseFragment
import com.yonder.addtolist.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding>() {

  val viewModel: SplashViewModel by viewModels()

  override fun initBinding(inflater: LayoutInflater, container: ViewGroup?)  =
    SplashFragmentBinding.inflate(layoutInflater)


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setObserver()

  }
  private fun setObserver() {
    lifecycleScope.launchWhenStarted {
      viewModel.state.collect { viewState ->
        when (viewState) {
          SplashViewState.GoLogin -> {
            findNavController().navigate(R.id.action_splash_to_login)
          }
          SplashViewState.GoShoppingListItems -> {
            findNavController().navigate(R.id.action_splash_to_shopping_list_items)
          }
          else -> Unit
        }
      }
    }
  }
}