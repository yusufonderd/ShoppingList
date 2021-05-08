package com.yonder.addtolist.base

/**
 * Yusuf Onder on 06,May,2021
 */

interface BaseNavigator {
  fun goBack()
  fun setBottomNavigationVisibility(isVisible: Boolean)
  fun onInitial() {

  }
}