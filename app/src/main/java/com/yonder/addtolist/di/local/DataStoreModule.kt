package com.yonder.addtolist.di.local

import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.data.local.UserPreferenceDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Yusuf Onder on 07,May,2021
 */



@[Module InstallIn(SingletonComponent::class)]
interface DataStoreModule {

  @get:[Binds Singleton]
  val UserPreferenceDataStoreImpl.userPreferenceDataStore: UserPreferenceDataStore
}