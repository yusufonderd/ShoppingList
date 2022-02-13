package com.yonder.addtolist.di

import com.yonder.addtolist.domain.usecase.CategoriesUseCase
import com.yonder.addtolist.domain.usecase.CategoriesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */

@[Module InstallIn(ViewModelComponent::class)]
interface SplashModule {

  @get:[Binds]
  val CategoriesUseCaseImpl.categoryUseCase: CategoriesUseCase

}
