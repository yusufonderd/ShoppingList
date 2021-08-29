package com.yonder.addtolist.core.network

import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.core.network.UserRegisterRequest
import com.yonder.addtolist.core.network.responses.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Yusuf Onder on 06,May,2021
 */
 interface LoginService {

 @POST("register")
 suspend fun registerGuestUser(@Body registerUser: UserRegisterRequest): BaseResponse<UserResponse>

}
