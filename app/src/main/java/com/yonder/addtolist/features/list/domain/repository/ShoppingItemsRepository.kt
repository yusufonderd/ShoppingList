package com.yonder.addtolist.features.list.domain.repository

import com.yonder.addtolist.core.base.BaseResponse
import com.yonder.addtolist.features.list.data.remote.response.CategoryProductResponse
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface ShoppingItemsRepository {
  suspend fun fetchShoppingItems(languageId: Int?): BaseResponse<ArrayList<CategoryProductResponse>>
}