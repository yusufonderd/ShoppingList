package com.yonder.addtolist.data.di

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yonder.addtolist.R
import com.yonder.addtolist.features.list.presentation.ShoppingListNavigatorImpl
import com.yonder.addtolist.features.list.presentation.ShoppingListNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


/**
 * Yusuf Onder on 06,May,2021
 */


@InstallIn(ActivityComponent::class)
@Module
interface NavigationModule {

  @get:Binds
  val ShoppingListNavigatorImpl.shoppingListNavigator: ShoppingListNavigator

  companion object {

    @Provides
    fun provideToolbar(activity: Activity): Toolbar = activity.findViewById(R.id.toolbar)

    @[Provides]
    fun provideNavController(activity: Activity): NavController =
      activity.findNavController(R.id.nav_host_container)

    @Provides
    fun provideBottomNavigationView(activity: Activity): BottomNavigationView =
      activity.findViewById(R.id.bottom_nav)
  }
}