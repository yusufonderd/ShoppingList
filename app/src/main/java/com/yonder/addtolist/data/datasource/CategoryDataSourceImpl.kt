package com.yonder.addtolist.data.datasource

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity
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

  override suspend fun getProductsByQuery(query: String, limit: Int): List<ProductEntitySummary> {
    return appDatabase.productDao().fetchProducts(1, query, limit)
  }

  override suspend fun getPopularProducts(): List<ProductEntitySummary> {
    return appDatabase.productDao().getPopularProducts(1)
  }

  override suspend fun delete(product: UserListProductEntity) {
    return appDatabase.userListProductDao().delete(product)
  }

  override suspend fun  insert(category: CategoryEntity) {
    return appDatabase.categoryDao().insert(category)
  }

  override suspend fun update(product: UserListProductEntity) {
    return appDatabase.userListProductDao().update(product)
  }

  override suspend fun insertAll(list: List<CategoryEntity>) {
    return appDatabase.categoryDao().insertAll(list);
  }

  override suspend fun getProductByEntity(productName: String, languageId: Int): ProductEntity? {
    return appDatabase.productDao().findProduct(languageId, productName);
  }

  override suspend fun insert(product: UserListProductEntity) {
    return appDatabase.userListProductDao().insert(product)
  }
}
