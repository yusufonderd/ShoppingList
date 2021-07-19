package com.yonder.addtolist.scenes.login.domain.repository

import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.core.network.UserRegisterRequest
import com.yonder.addtolist.core.network.responses.UserResponse

/**
 * Yusuf Onder on 12,May,2021
 */

interface LoginRepository {
  suspend fun login(params: UserRegisterRequest): BaseResponse<UserResponse>
}
