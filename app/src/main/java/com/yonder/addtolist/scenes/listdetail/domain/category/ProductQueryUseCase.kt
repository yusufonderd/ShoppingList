package com.yonder.addtolist.scenes.listdetail.domain.category

import com.yonder.addtolist.scenes.listdetail.domain.model.ProductEntityUiModel
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
interface ProductQueryUseCase {
  fun fetchProductByQuery(query: String,limit : Int): Flow<List<ProductEntityUiModel>>
  fun fetchPopularProducts(): Flow<List<ProductEntityUiModel>>
}
