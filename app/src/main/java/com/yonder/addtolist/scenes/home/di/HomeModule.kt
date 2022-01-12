package com.yonder.addtolist.scenes.home.di

import com.yonder.addtolist.scenes.home.data.UserListRepositoryImpl
import com.yonder.addtolist.scenes.home.data.local.datasource.UserListDataSource
import com.yonder.addtolist.scenes.home.data.local.datasource.UserListDataSourceImpl
import com.yonder.addtolist.scenes.home.data.remote.ApiService
import com.yonder.addtolist.scenes.home.domain.repository.UserListRepository

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * Yusuf Onder on 12,May,2021
 */

@[Module InstallIn(SingletonComponent::class)]
interface HomeModule {
  @get:[Binds]
  val UserListDataSourceImpl.shoppingDataSource: UserListDataSource

  @get:[Binds]
  val UserListRepositoryImpl.userRepository: UserListRepository

  companion object {

    @[Provides]
    fun provideCategoryService(retrofit: Retrofit): ApiService {
      return retrofit.create(ApiService::class.java)
    }
  }
}
