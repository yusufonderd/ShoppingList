package com.yonder.addtolist.domain.usecase

import com.yonder.core.network.RestResult
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.data.repository.CategoryListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */


class GetProductsWithCategory @Inject constructor(
    private val categoryListRepository: CategoryListRepository,
    private val dispatcher: CoroutineThread
) {
    fun getCategoriesWithProducts(): Flow<RestResult<List<CategoryWithProducts>>> {
        return categoryListRepository
            .fetchCategories()
            .flowOn(dispatcher.io)
    }
}

