package com.yonder.addtolist.scenes.listdetail.domain.category

import com.yonder.core.network.RestResult
import com.yonder.addtolist.local.entity.CategoryWithProducts
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
interface CategoryListRepository {
  fun fetchCategories(): Flow<RestResult<List<CategoryWithProducts>>>
}
