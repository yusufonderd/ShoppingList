package com.yonder.addtolist.features.list.di

import com.yonder.addtolist.features.list.data.local.datasource.ShoppingItemsDataSource
import com.yonder.addtolist.features.list.data.local.datasource.ShoppingItemsDataSourceImpl
import com.yonder.addtolist.features.list.data.remote.CategoryService
import com.yonder.addtolist.features.list.data.remote.datasource.ShoppingItemsRemoteDataSource
import com.yonder.addtolist.features.list.data.remote.datasource.ShoppingItemsRemoteDataSourceImpl
import com.yonder.addtolist.features.list.domain.repository.ShoppingItemsRepository
import com.yonder.addtolist.features.list.domain.repository.ShoppingItemsRepositoryImpl
import com.yonder.addtolist.features.list.domain.usecase.ShoppingItemsUseCase
import com.yonder.addtolist.features.list.domain.usecase.ShoppingItemsUseCaseImpl
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
  val ShoppingItemsRemoteDataSourceImpl.remoteLoginDataSource: ShoppingItemsRemoteDataSource

  @get:[Binds]
  val ShoppingItemsUseCaseImpl.shoppingUseCase: ShoppingItemsUseCase

  @get:[Binds]
  val ShoppingItemsDataSourceImpl.shoppingDataSource: ShoppingItemsDataSource

  @get:[Binds]
  val ShoppingItemsRepositoryImpl.shoppingRepository: ShoppingItemsRepository

  companion object{

    @[Provides]
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
      return retrofit.create(CategoryService::class.java)
    }
  }
}