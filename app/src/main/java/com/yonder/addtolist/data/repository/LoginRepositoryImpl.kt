package com.yonder.addtolist.data.repository

import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.core.network.UserRegisterRequest
import com.yonder.addtolist.core.network.responses.UserResponse
import com.yonder.addtolist.core.network.LoginService
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class LoginRepositoryImpl @Inject constructor(
  private val loginService: LoginService
) : LoginRepository {
  override suspend fun login(params: UserRegisterRequest): BaseResponse<UserResponse> =
    loginService.registerGuestUser(params)

  override suspend fun getCurrentUser(): BaseResponse<UserResponse> = loginService.getCurrentUser()

}
