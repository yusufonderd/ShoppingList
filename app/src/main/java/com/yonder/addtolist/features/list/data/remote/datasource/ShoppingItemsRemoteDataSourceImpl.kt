package com.yonder.addtolist.features.list.data.remote.datasource

import com.yonder.addtolist.features.list.data.remote.CategoryService
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class ShoppingItemsRemoteDataSourceImpl @Inject constructor(private val categoryService: CategoryService) :
  ShoppingItemsRemoteDataSource {

  override suspend fun fetchShoppingItems(languageId: Int?) =
    categoryService.fetchCategories(languageId)
}