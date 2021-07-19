package com.yonder.addtolist.scenes.list.data.local.datasource

import androidx.lifecycle.LiveData
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntity
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
class CategoryDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
  CategoryDataSource {
  override suspend fun getCategories(): List<CategoryWithProducts> {
    return appDatabase.categoryDao().getAllUserListWithProducts()
  }

  override suspend fun insertAllProducts(products: List<ProductEntity>) {
    return appDatabase.categoryDao().insertAll(products)
  }

  override suspend fun insert(category: CategoryEntity) {
    return appDatabase.categoryDao().insert(category)
  }

  override suspend fun insertAll(list: List<CategoryEntity>) {
    return appDatabase.categoryDao().insertAll(list);
  }
}