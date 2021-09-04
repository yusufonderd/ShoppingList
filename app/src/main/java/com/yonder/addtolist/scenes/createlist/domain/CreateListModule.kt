package com.yonder.addtolist.scenes.createlist.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */

@[Module InstallIn(ViewModelComponent::class)]
interface CreateListModule {

  @get:[Binds]
  val CreateListUseCaseImpl.createListUseCase: CreateListUseCase

}
