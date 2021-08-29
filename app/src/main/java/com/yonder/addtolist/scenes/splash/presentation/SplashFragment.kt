package com.yonder.addtolist.scenes.splash.presentation

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding>() {

  val viewModel: SplashViewModel by viewModels()

  override fun initBinding(inflater: LayoutInflater)  = SplashFragmentBinding.inflate(layoutInflater)

  override fun initViews() = Unit

  override fun initObservers() {
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
