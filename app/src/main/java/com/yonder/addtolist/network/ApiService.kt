package com.yonder.addtolist.network

import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.data.remote.response.CategoryProductResponse
import com.yonder.addtolist.data.remote.response.LanguageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Yusuf Onder on 12,May,2021
 */

interface ApiService {

  @GET("categories")
  suspend fun getCategories(@Query("language_id") languageId: Int?): BaseResponse<ArrayList<CategoryProductResponse>>

  @GET("languages")
  suspend fun getLanguages(): Response<BaseResponse<List<LanguageResponse>>>

}
