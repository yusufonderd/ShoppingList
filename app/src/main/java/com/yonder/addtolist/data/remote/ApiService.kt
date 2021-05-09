package com.yonder.addtolist.data.remote

import com.yonder.addtolist.core.BaseResponse
import com.yonder.addtolist.domain.model.UserRegisterParam
import com.yonder.addtolist.domain.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Yusuf Onder on 06,May,2021
 */
 interface ApiService {

 @POST("register")
 suspend fun registerGuestUser(@Body registerUser: UserRegisterParam): BaseResponse<UserResponse>

}