package com.yonder.addtolist.features.login.data.remote

import com.yonder.addtolist.core.base.BaseResponse
import com.yonder.addtolist.features.login.data.remote.request.UserRegisterRequest
import com.yonder.addtolist.features.login.data.remote.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Yusuf Onder on 06,May,2021
 */
 interface LoginService {

 @POST("register")
 suspend fun registerGuestUser(@Body registerUser: UserRegisterRequest): BaseResponse<UserResponse>

}