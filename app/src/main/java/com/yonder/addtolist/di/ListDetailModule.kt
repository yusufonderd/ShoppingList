package com.yonder.addtolist.di

import com.yonder.addtolist.scenes.listdetail.domain.category.CategoryListRepository
import com.yonder.addtolist.scenes.listdetail.domain.category.CategoryListRepositoryImpl
import com.yonder.addtolist.scenes.listdetail.domain.category.ProductQueryUseCase
import com.yonder.addtolist.scenes.listdetail.domain.category.ProductQueryUseCaseImpl
import com.yonder.addtolist.domain.usecase.ProductUseCase
import com.yonder.addtolist.domain.usecase.ProductUseCaseImpl
import com.yonder.addtolist.data.datasource.CategoryDataSource
import com.yonder.addtolist.data.datasource.CategoryDataSourceImpl
import com.yonder.addtolist.scenes.listdetail.domain.AddProductUseCase
import com.yonder.addtolist.scenes.listdetail.domain.AddProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@[Module InstallIn(SingletonComponent::class)]
interface ListDetailModule {

  @get:[Binds]
  val ProductQueryUseCaseImpl.useCase: ProductQueryUseCase

  @get:[Binds]
  val CategoryListRepositoryImpl.repository: CategoryListRepository

  @get:[Binds]
  val CategoryDataSourceImpl.dataSource: CategoryDataSource

  @get:[Binds]
  val ProductUseCaseImpl.productUseCase: ProductUseCase

  @get:[Binds]
  val AddProductUseCaseImpl.addProductUseCase: AddProductUseCase

}
