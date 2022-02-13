package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.base.NoInputUseCase
import com.yonder.core.network.RestResult
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.domain.mapper.LoginMapper
import com.yonder.addtolist.domain.uimodel.UserUiModel
import com.yonder.addtolist.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 17.11.2021
 */

class GetCurrentUser @Inject constructor(
    private val dispatcher: CoroutineThread,
    private val loginMapper: LoginMapper,
    private val loginRepository: LoginRepository,
    private val userPreferenceDataStore: UserPreferenceDataStore
) : NoInputUseCase<RestResult<UserUiModel>> {

  override suspend fun invoke(): Flow<RestResult<UserUiModel>> {
    return flow {
      emit(RestResult.Loading)
      val userUiModel = loginMapper.map(loginRepository.getCurrentUser())
      userPreferenceDataStore.setProviderType(userUiModel.providerType)
      userPreferenceDataStore.setProfileUrl(userUiModel.profileImage)
      userPreferenceDataStore.setFullName(userUiModel.fullName)
      emit(RestResult.Success(userUiModel))
    }.catch { error ->
      emit(RestResult.Error(error))
    }.flowOn(dispatcher.io)
  }

}
