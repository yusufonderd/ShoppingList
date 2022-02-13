package com.yonder.addtolist.di

import com.yonder.addtolist.data.datasource.RemoteSettingsDataSource
import com.yonder.addtolist.data.datasource.RemoteSettingsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Yusuf Onder on 12,May,2021
 */

@[Module InstallIn(ViewModelComponent::class)]
interface SettingsModule {

  @get:[Binds]
  val RemoteSettingsDataSourceImpl.remoteSettingsDataSource: RemoteSettingsDataSource
}
