package com.yonder.addtolist.features.login.domain.usecase

import com.yonder.addtolist.core.NetworkResult
import com.yonder.addtolist.data.di.thread.CoroutineThread
import com.yonder.addtolist.features.login.domain.mapper.LoginMapper
import com.yonder.addtolist.features.login.data.remote.request.UserRegisterRequest
import com.yonder.addtolist.features.login.domain.model.UserUiModel
import com.yonder.addtolist.features.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class LoginUseCaseImpl @Inject constructor(
  private val repository: LoginRepository,
  private val loginMapper: LoginMapper,
  private val dispatcher: CoroutineThread
) : LoginUseCase {

  override fun login(userRegisterParam: UserRegisterRequest): Flow<NetworkResult<UserUiModel>> {
    return flow {
      emit(NetworkResult.Loading)
      emit(NetworkResult.Success(loginMapper.map(repository.login(userRegisterParam))))
    }.catch { error ->
      emit(NetworkResult.Error(error))
    }.flowOn(dispatcher.io)
  }

}