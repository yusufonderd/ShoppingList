package com.yonder.addtolist.scenes.productdetail.di

import com.yonder.addtolist.scenes.productdetail.domain.usecase.LocalProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.usecase.LocalProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */


@[Module InstallIn(ViewModelComponent::class)]
interface ProductDetailModule {

  @get:[Binds]
  val LocalProductUseCaseImpl.localProductUseCase: LocalProductUseCase

}
