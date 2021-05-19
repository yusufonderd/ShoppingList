package com.yonder.addtolist.features.list.domain.usecase

import com.yonder.addtolist.core.NetworkResult
import com.yonder.addtolist.features.list.domain.model.CategoryProductsUiModel
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface ShoppingItemsUseCase {
  fun fetchShoppingItems(languageId: Int?): Flow<NetworkResult<CategoryProductsUiModel>>
}