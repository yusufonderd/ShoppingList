package com.yonder.addtolist.scenes.list.data.remote

import com.yonder.addtolist.core.network.request.CreateUserListProductRequest
import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.core.network.request.UserListProductRequest
import com.yonder.addtolist.scenes.list.data.remote.response.CategoryProductResponse
import com.yonder.addtolist.scenes.list.data.remote.response.UserListProductResponse
import com.yonder.addtolist.scenes.list.data.remote.response.UserListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Yusuf Onder on 12,May,2021
 */

interface ApiService {

  @PUT("newUserListProduct/{productId}")
  suspend fun updateProduct(
    @Path("productId") productId: Int?,
    @Body userListProductRequest: UserListProductRequest
  ): BaseResponse<UserListProductResponse>

  @DELETE("newUserListProduct/{productId}")
  suspend fun removeProduct(
    @Path("productId") productId: Int?
  ): BaseResponse<UserListProductResponse>

  @POST("newUserListProduct")
  suspend fun createProduct(@Body request: CreateUserListProductRequest): BaseResponse<UserListProductResponse>

  @GET("categories")
  suspend fun getCategories(@Query("language_id") languageId: Int?): BaseResponse<ArrayList<CategoryProductResponse>>

  @GET("newLists/{listId}")
  suspend fun fetchUserList(@Path("listId") listId: Int?): BaseResponse<UserListResponse>

  @GET("newLists")
  suspend fun getUserLists(): BaseResponse<List<UserListResponse>>

  @POST("newLists")
  suspend fun createUserList(@Body request: CreateUserListRequest): BaseResponse<UserListResponse>

}