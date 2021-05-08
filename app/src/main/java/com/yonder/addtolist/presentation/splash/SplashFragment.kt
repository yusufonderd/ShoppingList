package com.yonder.addtolist.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yonder.addtolist.R
import com.yonder.addtolist.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

  val viewModel: SplashViewModel by viewModels()

  @Inject
  lateinit var splashNavigator: SplashNavigator

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.splash_fragment, container, false)
  }

  override fun setObserver() {
    lifecycleScope.launchWhenStarted {
      viewModel.state.collect { viewState ->

        when (viewState) {
          SplashViewState.GoLogin -> {
            splashNavigator.navigateLogin()
          }
          SplashViewState.GoHome -> {
            splashNavigator.navigateShoppingListItems()
          }
          else -> Unit
        }
      }
    }
  }


}