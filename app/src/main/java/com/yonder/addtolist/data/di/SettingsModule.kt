package com.yonder.addtolist.data.di

import com.yonder.addtolist.data.remote.datasource.settings.RemoteSettingsDataSource
import com.yonder.addtolist.data.remote.datasource.settings.RemoteSettingsDataSourceImpl
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