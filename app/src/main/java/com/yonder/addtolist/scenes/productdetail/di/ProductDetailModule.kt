package com.yonder.addtolist.scenes.productdetail.di

import com.yonder.addtolist.scenes.listdetail.domain.product.ProductRepository
import com.yonder.addtolist.scenes.listdetail.domain.product.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */


@[Module InstallIn(SingletonComponent::class)]
interface ProductDetailModule {

  @get:[Binds]
  val ProductRepositoryImpl.productRepository: ProductRepository

}
