package com.yonder.addtolist.scenes.detail.domain

import com.yonder.addtolist.scenes.detail.domain.category.CategoryListRepository
import com.yonder.addtolist.scenes.detail.domain.category.CategoryListRepositoryImpl
import com.yonder.addtolist.scenes.detail.domain.category.ProductQueryUseCase
import com.yonder.addtolist.scenes.detail.domain.category.ProductQueryUseCaseImpl
import com.yonder.addtolist.scenes.detail.domain.product.ProductUseCase
import com.yonder.addtolist.scenes.detail.domain.product.ProductUseCaseImpl
import com.yonder.addtolist.scenes.detail.domain.product.ProductRepository
import com.yonder.addtolist.scenes.detail.domain.product.ProductRepositoryImpl
import com.yonder.addtolist.scenes.home.data.local.datasource.CategoryDataSource
import com.yonder.addtolist.scenes.home.data.local.datasource.CategoryDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@[Module InstallIn(ViewModelComponent::class)]
interface ListDetailModule {

  @get:[Binds]
  val ProductQueryUseCaseImpl.useCase: ProductQueryUseCase

  @get:[Binds]
  val CategoryListRepositoryImpl.repository: CategoryListRepository

  @get:[Binds]
  val CategoryDataSourceImpl.dataSource: CategoryDataSource

  @get:[Binds]
  val ProductRepositoryImpl.productRepository: ProductRepository

  @get:[Binds]
  val ProductUseCaseImpl.addProductUseCase: ProductUseCase

}
