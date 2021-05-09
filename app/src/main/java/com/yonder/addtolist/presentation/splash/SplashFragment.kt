package com.yonder.addtolist.presentation.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yonder.addtolist.core.base.BaseFragment
import com.yonder.addtolist.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding>() {

  @Inject
  lateinit var splashNavigator: SplashNavigator

  val viewModel: SplashViewModel by viewModels()

  override fun initBinding(inflater: LayoutInflater, container: ViewGroup?)  =
    SplashFragmentBinding.inflate(layoutInflater)

  override fun setupViews() {

  }
  override fun setObserver() {
    lifecycleScope.launchWhenStarted {
      viewModel.state.collect { viewState ->

        when (viewState) {
          SplashViewState.GoLogin -> {
            splashNavigator.navigateLogin()
          }
          SplashViewState.GoShoppingListItems -> {
            splashNavigator.navigateShoppingListItems()
          }
          else -> Unit
        }
      }
    }
  }


}