package com.yonder.addtolist.features.list.data.local.datasource

import com.yonder.addtolist.features.list.data.local.entity.CategoryEntity

/**
 * Yusuf Onder on 12,May,2021
 */

interface ShoppingItemsDataSource {
  suspend fun insertCategory(category: CategoryEntity)
  suspend fun fetchCategory(): List<CategoryEntity>
}