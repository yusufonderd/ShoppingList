package com.yonder.addtolist.features.settings.di

import com.yonder.addtolist.features.settings.data.remote.datasource.RemoteSettingsDataSource
import com.yonder.addtolist.features.settings.data.remote.datasource.RemoteSettingsDataSourceImpl
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