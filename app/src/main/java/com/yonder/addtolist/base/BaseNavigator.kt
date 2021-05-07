package com.yonder.addtolist.base

/**
 * Yusuf Onder on 06,May,2021
 */

interface BaseNavigator {
  fun goLogin()
  fun goSplash()
  fun goBack()
  fun setBottomNavigationVisibility(isVisible : Boolean)
}