package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.NetworkResult
import com.yonder.addtolist.data.di.thread.CoroutineThread
import com.yonder.addtolist.domain.mapper.LoginMapper
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import com.yonder.addtolist.domain.repository.LoginRepository
import com.yonder.addtolist.domain.model.ui.UserUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Yusuf Onder on 09,May,2021
 */

interface LoginUseCase {
  fun login(userRegisterParam: UserRegisterRequest): Flow<NetworkResult<UserUiModel>>
}

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