package com.yonder.addtolist.di

import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.domain.mapper.LanguageResponseMapper
import com.yonder.addtolist.data.remote.response.LanguageResponse
import com.yonder.addtolist.domain.uimodel.LanguageUiModel
import com.yonder.addtolist.data.repository.LanguageRepository
import com.yonder.addtolist.data.repository.LanguageRepositoryImpl
import com.yonder.addtolist.domain.usecase.GetLanguageUseCase
import com.yonder.addtolist.domain.usecase.GetLanguageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */

@[Module InstallIn(ViewModelComponent::class)]
interface LanguageModule {

  @get:[Binds]
  val LanguageResponseMapper.responseMapper: Mapper<LanguageResponse, LanguageUiModel>

  @get:[Binds]
  val GetLanguageUseCaseImpl.getLanguageUseCase: GetLanguageUseCase

  @get:[Binds]
  val LanguageRepositoryImpl.languageRepository: LanguageRepository

}
