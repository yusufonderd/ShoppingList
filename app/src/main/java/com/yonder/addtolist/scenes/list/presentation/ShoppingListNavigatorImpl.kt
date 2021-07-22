package com.yonder.addtolist.scenes.list.presentation

import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yonder.addtolist.R
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.scenes.detail.ListDetailFragmentArgs
import javax.inject.Inject
import javax.inject.Provider

/**
 * Yusuf Onder on 06,May,2021
 */

class ShoppingListNavigatorImpl @Inject constructor(
  private val navController: Provider<NavController>,
  private val bottomNavigationView: Provider<BottomNavigationView>,
  private val toolbar: Provider<Toolbar>
) :
  ShoppingListNavigator {

  override fun navigateSplash() {
    setBottomNavigationVisibility(false)
    navController.get().navigate(R.id.action_shopping_list_items_to_splash)
  }

  override fun goBack() {
    navController.get().navigateUp()
  }

  override fun navigateLogin() {
    setBottomNavigationVisibility(false)
    navController.get().navigate(R.id.action_shopping_list_items_to_login)
  }

  override fun setBottomNavigationVisibility(isVisible: Boolean) {
    bottomNavigationView.get().isVisible = isVisible
  }

  override fun setToolbarVisibility(isVisible: Boolean) {
    toolbar.get().isVisible = isVisible
  }

  override fun navigateList(userListEntity: UserListEntity) {
    setBottomNavigationVisibility(false)
    navController.get().navigate(
      ShoppingListFragmentDirections.actionShoppingListToListDetail(
        userList = userListEntity,
        title = userListEntity.name
      )
    )
  }

  override fun navigateCreateListFragment() {
    setBottomNavigationVisibility(false)
    navController.get().navigate(R.id.action_shopping_list_to_create_list)
  }

}