package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.common.utils.device.LanguageUtils
import com.yonder.addtolist.core.data.State
import com.yonder.addtolist.core.data.map
import com.yonder.core.base.mapper.ListMapperImpl
import com.yonder.addtolist.domain.mapper.LanguageResponseMapper
import com.yonder.addtolist.domain.uimodel.LanguageUiModel
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
            state.map { response ->
                ListMapperImpl(mapper).map(response
                    .filter {
                        it.tag == LanguageUtils.TR.first ||
                                it.tag == LanguageUtils.EN.first ||
                                it.tag == LanguageUtils.DE.first
                    }
                )
            }
        }
    }
}

interface GetLanguageUseCase {
    suspend operator fun invoke(): Flow<State<List<LanguageUiModel>>>
}
