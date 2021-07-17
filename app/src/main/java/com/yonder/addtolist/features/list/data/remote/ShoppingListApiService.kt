package com.yonder.addtolist.features.list.data.remote

import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.features.list.data.remote.response.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Yusuf Onder on 12,May,2021
 */

interface ShoppingListApiService {

  /*/
    @GET("categories")
    suspend fun fetchCategories(@Query("language_id") languageId: Int?): BaseResponse<ArrayList<CategoryProductResponse>>
  */

  @GET("newLists/{listId}")
  suspend fun fetchUserList(@Path("listId") listId: Int?): BaseResponse<UserListResponse>

  @GET("newLists")
  suspend fun fetchUserLists(): BaseResponse<List<UserListResponse>>

}