package com.yonder.addtolist.scenes.detail.domain.category

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntitySummary
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
interface CategoryListRepository {
  fun fetchCategories(): Flow<Result<List<CategoryWithProducts>>>
}
