package com.yonder.addtolist.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.ProductMinimal

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
@Dao
interface ProductDao {

  @Query("SELECT name,categoryImage FROM category_products WHERE languageId  = :languageId and popular = 1 ORDER BY name ")
  fun getPopularProducts(languageId: Int): LiveData<List<ProductMinimal>>

  @Query("SELECT name,categoryImage FROM category_products WHERE languageId  = :languageId and name LIKE '%' || :query || '%'  ORDER BY name LIMIT :limit ")
  fun fetchProducts(languageId: Int, query: String, limit: Int = 10): LiveData<List<ProductMinimal>>

  @Query("SELECT * FROM category_products WHERE languageId  = :languageId and name = :name LIMIT 1 ")
  suspend fun findProduct(languageId: Int, name: String): ProductEntity?

}