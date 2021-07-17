package com.yonder.addtolist.features.login.domain.repository

import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.features.login.data.remote.datasource.RemoteLoginDataSource
import com.yonder.addtolist.features.login.data.remote.request.UserRegisterRequest
import com.yonder.addtolist.features.login.data.remote.response.UserResponse
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class LoginRepositoryImpl @Inject constructor(
  private val remoteLoginDataSource: RemoteLoginDataSource
) : LoginRepository {
  override suspend fun login(params: UserRegisterRequest): BaseResponse<UserResponse> =
    remoteLoginDataSource.register(params)
}
