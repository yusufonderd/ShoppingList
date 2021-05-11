package com.yonder.addtolist.data.remote

import com.yonder.addtolist.core.base.BaseResponse
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import com.yonder.addtolist.domain.model.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Yusuf Onder on 06,May,2021
 */
 interface ApiService {

 @POST("register")
 suspend fun registerGuestUser(@Body registerUser: UserRegisterRequest): BaseResponse<UserResponse>

}