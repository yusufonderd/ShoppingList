package com.yonder.addtolist.features.list.data.local.datasource

import com.yonder.addtolist.data.remote.local.AppDatabase
import com.yonder.addtolist.features.list.data.local.entity.CategoryEntity
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class ShoppingItemsDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
  ShoppingItemsDataSource {

  override suspend fun insertCategory(category: CategoryEntity) =
    appDatabase.categoryDao().insert(category)

  override suspend fun fetchCategory(): List<CategoryEntity> =
    appDatabase.categoryDao().fetchCategory()

}