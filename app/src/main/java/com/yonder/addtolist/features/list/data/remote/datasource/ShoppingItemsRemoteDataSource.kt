package com.yonder.addtolist.features.list.data.remote.datasource

import com.yonder.addtolist.core.base.BaseResponse
import com.yonder.addtolist.features.list.data.remote.response.CategoryProductResponse

/**
 * Yusuf Onder on 12,May,2021
 */

interface ShoppingItemsRemoteDataSource {
  suspend fun fetchShoppingItems(languageId: Int?): BaseResponse<ArrayList<CategoryProductResponse>>
}