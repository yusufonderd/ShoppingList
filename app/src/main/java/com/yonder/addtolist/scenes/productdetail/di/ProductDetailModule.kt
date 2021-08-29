package com.yonder.addtolist.scenes.productdetail.di

import com.yonder.addtolist.scenes.productdetail.domain.DeleteProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.DeleteProductUseCaseImpl
import com.yonder.addtolist.scenes.productdetail.domain.GetCategoriesUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetCategoriesUseCaseImpl
import com.yonder.addtolist.scenes.productdetail.domain.GetProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetProductUseCaseImpl
import com.yonder.addtolist.scenes.productdetail.domain.UpdateProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.UpdateProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */


@[Module InstallIn(SingletonComponent::class)]
interface ProductDetailModule {

  @get:[Binds Singleton]
  val DeleteProductUseCaseImpl.deleteProduct: DeleteProductUseCase

  @get:[Binds Singleton]
  val UpdateProductUseCaseImpl.updateProduct: UpdateProductUseCase

  @get:[Binds Singleton]
  val GetProductUseCaseImpl.getProduct: GetProductUseCase

  @get:[Binds Singleton]
  val GetCategoriesUseCaseImpl.getCategories: GetCategoriesUseCase


}
