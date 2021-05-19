package com.yonder.addtolist.features.list.data.remote

import com.yonder.addtolist.core.base.BaseResponse
import com.yonder.addtolist.features.list.data.remote.response.CategoryProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Yusuf Onder on 12,May,2021
 */

interface CategoryService {
  @GET("categories")
  suspend fun fetchCategories(@Query("language_id") languageId: Int?): BaseResponse<ArrayList<CategoryProductResponse>>
}