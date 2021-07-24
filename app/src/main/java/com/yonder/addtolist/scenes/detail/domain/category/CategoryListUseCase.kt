package com.yonder.addtolist.scenes.detail.domain.category

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntitySummary
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
interface CategoryListUseCase {
  fun getCategories(): Flow<Result<List<CategoryWithProducts>>>
  fun fetchProductByQuery(query: String): Flow<List<ProductEntitySummary>>
  fun fetchPopularProducts(): Flow<List<ProductEntitySummary>>

}