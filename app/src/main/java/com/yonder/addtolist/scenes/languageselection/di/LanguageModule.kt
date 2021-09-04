package com.yonder.addtolist.scenes.languageselection.di

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.scenes.languageselection.data.mapper.LanguageResponseMapper
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageResponse
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageUiModel
import com.yonder.addtolist.scenes.languageselection.data.repository.LanguageRepository
import com.yonder.addtolist.scenes.languageselection.data.repository.LanguageRepositoryImpl
import com.yonder.addtolist.scenes.languageselection.domain.GetLanguageUseCase
import com.yonder.addtolist.scenes.languageselection.domain.GetLanguageUseCaseImpl
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
