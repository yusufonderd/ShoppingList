package com.yonder.addtolist.presentation.list

import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yonder.addtolist.R
import javax.inject.Inject
import javax.inject.Provider

/**
 * Yusuf Onder on 06,May,2021
 */

class ShoppingListNavigatorImpl @Inject constructor(
  private val navController: Provider<NavController>,
  private val bottomNavigationView: BottomNavigationView
) :
  ShoppingListNavigator {

  override fun goSplash() {
    setBottomNavigationVisibility(false)
    navController.get().navigate(R.id.action_shopping_list_items_to_splash)
  }

  override fun goBack() {
    navController.get().navigateUp()
  }

  override fun setBottomNavigationVisibility(isVisible: Boolean) {
    bottomNavigationView.isVisible = isVisible
  }

}