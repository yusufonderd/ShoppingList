package com.yonder.addtolist.scenes.login.domain.usecase

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.scenes.login.domain.mapper.LoginMapper
import com.yonder.addtolist.core.network.UserRegisterRequest
import com.yonder.addtolist.scenes.login.domain.model.UserUiModel
import com.yonder.addtolist.scenes.login.domain.repository.LoginRepository
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

  override fun login(userRegisterParam: UserRegisterRequest): Flow<Result<UserUiModel>> {
    return flow {
      emit(Result.Loading)
      emit(Result.Success(loginMapper.map(repository.login(userRegisterParam))))
    }.catch { error ->
      emit(Result.Error(error))
    }.flowOn(dispatcher.io)
  }

}