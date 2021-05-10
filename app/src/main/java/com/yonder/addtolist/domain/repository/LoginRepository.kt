package com.yonder.addtolist.domain.repository

import com.yonder.addtolist.core.BaseResponse
import com.yonder.addtolist.data.remote.ApiService
import com.yonder.addtolist.data.remote.datasource.login.RemoteLoginDataSource
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import com.yonder.addtolist.domain.model.response.UserResponse
import javax.inject.Inject

/**
 * Yusuf Onder on 09,May,2021
 */

interface LoginRepository {
  suspend fun login(params: UserRegisterRequest): BaseResponse<UserResponse>
}

class LoginRepositoryImpl @Inject constructor(
  private val remoteLoginDataSource: RemoteLoginDataSource
) : LoginRepository {
  override suspend fun login(params: UserRegisterRequest): BaseResponse<UserResponse> =
    remoteLoginDataSource.register(params)
}
