package com.yonder.addtolist.data.remote.datasource.login

import com.yonder.addtolist.core.BaseResponse
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import com.yonder.addtolist.domain.model.response.UserResponse

/**
 * Yusuf Onder on 10,May,2021
 */

interface RemoteLoginDataSource {
  suspend fun register(registerRequest: UserRegisterRequest): BaseResponse<UserResponse>
}