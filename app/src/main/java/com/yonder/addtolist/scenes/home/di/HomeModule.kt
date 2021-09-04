package com.yonder.addtolist.scenes.home.di

import com.yonder.addtolist.scenes.home.data.local.datasource.UserListDataSource
import com.yonder.addtolist.scenes.home.data.local.datasource.UserListDataSourceImpl
import com.yonder.addtolist.scenes.home.data.remote.ApiService
import com.yonder.addtolist.scenes.home.domain.repository.UserListRepository
import com.yonder.addtolist.scenes.home.data.UserListRepositoryImpl
import com.yonder.addtolist.scenes.home.domain.usecase.CreateListUseCase
import com.yonder.addtolist.scenes.home.domain.usecase.CreateListUseCaseImpl
import com.yonder.addtolist.scenes.home.domain.usecase.GetUserListsUseCase
import com.yonder.addtolist.scenes.home.domain.usecase.GetUserListsUseCaseImpl
import com.yonder.addtolist.scenes.home.domain.usecase.GetUserListUseCase
import com.yonder.addtolist.scenes.home.domain.usecase.GetUserListUseCaseImpl
import com.yonder.addtolist.scenes.home.domain.usecase.GetUserListProductUseCase
import com.yonder.addtolist.scenes.home.domain.usecase.GetUserListProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * Yusuf Onder on 12,May,2021
 */

@[Module InstallIn(SingletonComponent::class)]
interface HomeModule {

  @get:[Binds]
  val CreateListUseCaseImpl.createListUseCase: CreateListUseCase

  @get:[Binds]
  val GetUserListsUseCaseImpl.getUserListsUseCase: GetUserListsUseCase

  @get:[Binds]
  val UserListDataSourceImpl.shoppingDataSource: UserListDataSource

  @get:[Binds]
  val UserListRepositoryImpl.userRepository: UserListRepository

  @get:[Binds]
  val GetUserListUseCaseImpl.getUserUserListUseCase: GetUserListUseCase

  @get:[Binds]
  val GetUserListProductUseCaseImpl.getUserListProductUseCase: GetUserListProductUseCase

  companion object {

    @[Provides]
    fun provideCategoryService(retrofit: Retrofit): ApiService {
      return retrofit.create(ApiService::class.java)
    }
  }
}
