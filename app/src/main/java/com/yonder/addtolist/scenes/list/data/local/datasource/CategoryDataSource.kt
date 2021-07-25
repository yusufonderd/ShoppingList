package com.yonder.addtolist.scenes.list.data.local.datasource

import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
interface CategoryDataSource {
  suspend fun insert(category: CategoryEntity)
  suspend fun insertAll(list: List<CategoryEntity>)
  suspend fun insertAllProducts(products: List<ProductEntity>)
  suspend fun getCategories(): List<CategoryWithProducts>
  suspend fun getProductsByQuery(query: String,limit :Int): List<ProductEntitySummary>
  suspend fun getPopularProducts(): List<ProductEntitySummary>
  suspend fun insert(product: UserListProductEntity)
  suspend fun delete(product: UserListProductEntity)
  suspend fun update(product: UserListProductEntity)
  suspend fun getProductByEntity(productName: String,languageId: Int) : ProductEntity?
}