package com.yonder.addtolist.data.di

import android.content.Context
import android.content.SharedPreferences
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.data.local.UserPreferenceDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Yusuf Onder on 07,May,2021
 */



@[Module InstallIn(SingletonComponent::class)]
interface DataStoreModule {

  @get:[Binds Singleton]
  val UserPreferenceDataStoreImpl.userPreferenceDataStore: UserPreferenceDataStore

  companion object{
    @[Provides]
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
      return context.getSharedPreferences(UserPreferenceDataStoreImpl.KEY_APP_PREFERENCES, Context.MODE_PRIVATE)
    }
  }
}