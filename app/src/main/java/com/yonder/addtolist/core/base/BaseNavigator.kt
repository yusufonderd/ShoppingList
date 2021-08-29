package com.yonder.addtolist.core.base

/**
 * Yusuf Onder on 06,May,2021
 */

interface BaseNavigator {
  fun goBack()
  fun setBottomNavigationVisibility(isVisible: Boolean)
  fun setToolbarVisibility(isVisible: Boolean)
  fun onInitial() {

  }
}
