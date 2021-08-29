package com.yonder.addtolist.scenes.listdetail.domain.category

import com.yonder.addtolist.local.entity.ProductEntitySummary
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
interface ProductQueryUseCase {
  fun fetchProductByQuery(query: String,limit : Int): Flow<List<ProductEntitySummary>>
  fun fetchPopularProducts(): Flow<List<ProductEntitySummary>>
}
