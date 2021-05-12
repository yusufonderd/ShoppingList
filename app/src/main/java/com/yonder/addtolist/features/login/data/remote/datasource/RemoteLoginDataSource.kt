package com.yonder.addtolist.features.login.data.remote.datasource

import com.yonder.addtolist.core.base.BaseResponse
import com.yonder.addtolist.features.login.data.remote.request.UserRegisterRequest
import com.yonder.addtolist.features.login.data.remote.response.UserResponse

/**
 * Yusuf Onder on 10,May,2021
 */

interface RemoteLoginDataSource {
  suspend fun register(registerRequest: UserRegisterRequest): BaseResponse<UserResponse>
}