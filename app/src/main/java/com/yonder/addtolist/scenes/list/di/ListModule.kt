package com.yonder.addtolist.scenes.list.di

import com.yonder.addtolist.scenes.list.data.local.datasource.UserListDataSource
import com.yonder.addtolist.scenes.list.data.local.datasource.UserListDataSourceImpl
import com.yonder.addtolist.scenes.list.data.remote.ApiService
import com.yonder.addtolist.scenes.list.domain.repository.UserListRepository
import com.yonder.addtolist.scenes.list.data.UserListRepositoryImpl
import com.yonder.addtolist.scenes.list.domain.usecase.LocalListUseCase
import com.yonder.addtolist.scenes.list.domain.usecase.LocalListUseCaseImpl
import com.yonder.addtolist.scenes.list.domain.usecase.LocalUserListProductUseCase
import com.yonder.addtolist.scenes.list.domain.usecase.LocalUserListProductUseCaseImpl
import com.yonder.addtolist.scenes.list.domain.usecase.UserListUseCaseImpl
import com.yonder.addtolist.scenes.list.domain.usecase.UserListUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

/**
 * Yusuf Onder on 12,May,2021
 */

@[Module InstallIn(ViewModelComponent::class)]
interface ListModule {

  @get:[Binds]
  val UserListUseCaseImpl.shoppingUseCase: UserListUseCase

  @get:[Binds]
  val UserListDataSourceImpl.shoppingDataSource: UserListDataSource

  @get:[Binds]
  val UserListRepositoryImpl.userRepository: UserListRepository

  @get:[Binds]
  val LocalListUseCaseImpl.localUserListUseCase: LocalListUseCase

  @get:[Binds]
  val LocalUserListProductUseCaseImpl.localUserListProductUseCase: LocalUserListProductUseCase

  companion object{

    @[Provides]
    fun provideCategoryService(retrofit: Retrofit): ApiService {
      return retrofit.create(ApiService::class.java)
    }
  }
}