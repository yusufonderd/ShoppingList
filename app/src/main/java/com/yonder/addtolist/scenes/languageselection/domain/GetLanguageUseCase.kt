package com.yonder.addtolist.scenes.languageselection.domain

import com.yonder.addtolist.core.data.State
import com.yonder.addtolist.core.data.map
import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.scenes.languageselection.data.mapper.LanguageResponseMapper
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageUiModel
import com.yonder.addtolist.scenes.languageselection.data.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */
class GetLanguageUseCaseImpl @Inject constructor(
  private val languageRepository: LanguageRepository,
  private val mapper: LanguageResponseMapper
) : GetLanguageUseCase {
  override suspend fun invoke(): Flow<State<List<LanguageUiModel>>> {
    return languageRepository.fetchLanguages().map { state ->
      state.map {
        ListMapperImpl(mapper).map(it)
      }
    }
  }
}

interface GetLanguageUseCase {
  suspend operator fun invoke(): Flow<State<List<LanguageUiModel>>>
}
