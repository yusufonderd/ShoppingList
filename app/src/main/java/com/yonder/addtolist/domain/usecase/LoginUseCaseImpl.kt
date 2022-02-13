package com.yonder.addtolist.domain.usecase

import com.yonder.core.network.RestResult
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.domain.mapper.LoginMapper
import com.yonder.addtolist.core.network.UserRegisterRequest
import com.yonder.addtolist.domain.uimodel.UserUiModel
import com.yonder.addtolist.data.repository.LoginRepository
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

  override operator fun invoke(userRegisterParam: UserRegisterRequest): Flow<RestResult<UserUiModel>> {
    return flow {
      emit(RestResult.Loading)
      emit(RestResult.Success(loginMapper.map(repository.login(userRegisterParam))))
    }.catch { error ->
      emit(RestResult.Error(error))
    }.flowOn(dispatcher.io)
  }

}
