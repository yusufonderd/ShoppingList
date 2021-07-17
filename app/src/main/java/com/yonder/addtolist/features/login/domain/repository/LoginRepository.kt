package com.yonder.addtolist.features.login.domain.repository

import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.features.login.data.remote.request.UserRegisterRequest
import com.yonder.addtolist.features.login.data.remote.response.UserResponse

/**
 * Yusuf Onder on 12,May,2021
 */

interface LoginRepository {
  suspend fun login(params: UserRegisterRequest): BaseResponse<UserResponse>
}
