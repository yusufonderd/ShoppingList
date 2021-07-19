package com.yonder.addtolist.scenes.list.di

import com.yonder.addtolist.scenes.list.data.local.datasource.UserListDataSource
import com.yonder.addtolist.scenes.list.data.local.datasource.UserListDataSourceImpl
import com.yonder.addtolist.scenes.list.data.remote.ShoppingListApiService
import com.yonder.addtolist.scenes.list.domain.repository.UserListRepository
import com.yonder.addtolist.scenes.list.data.UserListRepositoryImpl
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

  companion object{

    @[Provides]
    fun provideCategoryService(retrofit: Retrofit): ShoppingListApiService {
      return retrofit.create(ShoppingListApiService::class.java)
    }
  }
}