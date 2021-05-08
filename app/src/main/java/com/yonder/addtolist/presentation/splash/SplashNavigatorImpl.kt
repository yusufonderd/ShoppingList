package com.yonder.addtolist.presentation.splash

import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject
import javax.inject.Provider

/**
 * Yusuf Onder on 08,May,2021
 */

class SplashNavigatorImpl @Inject constructor(
  private val navController: Provider<NavController>,
  private val bottomNavigationView: Provider<BottomNavigationView>
): SplashNavigator{

  init {
    onInitial()
  }

  override fun goBack() {

  }

  override fun onInitial() {
    super.onInitial()
    setBottomNavigationVisibility(false)
  }

  override fun goLogin() {

  }

  override fun setBottomNavigationVisibility(isVisible: Boolean) {
    bottomNavigationView.get().isVisible = isVisible
  }

}