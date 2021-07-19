package com.yonder.addtolist.scenes.detail.domain

import com.yonder.addtolist.scenes.list.data.local.datasource.CategoryDataSource
import com.yonder.addtolist.scenes.list.data.local.datasource.CategoryDataSourceImpl
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
  val CategoryListUseCaseImpl.useCase: CategoryListUseCase

  @get:[Binds]
  val CategoryListRepositoryImpl.repository: CategoryListRepository

  @get:[Binds]
  val CategoryDataSourceImpl.dataSource: CategoryDataSource

}