package com.yonder.addtolist.di.navigation

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yonder.addtolist.R
import com.yonder.addtolist.presentation.list.ShoppingListNavigatorImpl
import com.yonder.addtolist.presentation.list.ShoppingListNavigator
import com.yonder.addtolist.presentation.splash.SplashNavigator
import com.yonder.addtolist.presentation.splash.SplashNavigatorImpl

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Yusuf Onder on 06,May,2021
 */


@InstallIn(ActivityComponent::class)
@Module
interface NavigationModule {

  @get:Binds
  val ShoppingListNavigatorImpl.shoppingListNavigator: ShoppingListNavigator

  @get:Binds
  val SplashNavigatorImpl.splashNavigator: SplashNavigator

  companion object {
    @[Provides]
    fun provideNavController(activity: Activity): NavController =
      activity.findNavController(R.id.nav_host_container)

    @Provides
    fun provideBottomNavigationView(activity: Activity): BottomNavigationView =
      activity.findViewById<BottomNavigationView>(R.id.bottom_nav)
  }
}