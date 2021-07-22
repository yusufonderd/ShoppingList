package com.yonder.addtolist.scenes.list.presentation

import com.yonder.addtolist.core.base.BaseNavigator
import com.yonder.addtolist.local.entity.UserListEntity

/**
 * Yusuf Onder on 06,May,2021
 */
interface ShoppingListNavigator : BaseNavigator {
  fun navigateSplash()
  fun navigateLogin()
  fun navigateCreateListFragment()
  fun navigateList(userListEntity: UserListEntity)
}