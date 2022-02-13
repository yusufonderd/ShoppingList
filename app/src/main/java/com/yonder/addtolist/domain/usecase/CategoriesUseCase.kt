package com.yonder.addtolist.domain.usecase

import com.yonder.core.network.RestResult
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.scenes.listdetail.domain.category.CategoryListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */

interface CategoriesUseCase {
  fun getCategories(): Flow<RestResult<List<CategoryWithProducts>>>
}

class CategoriesUseCaseImpl @Inject constructor(
  private val categoryListRepository: CategoryListRepository,
  private val dispatcher: CoroutineThread
) : CategoriesUseCase {
  override fun getCategories(): Flow<RestResult<List<CategoryWithProducts>>> {
    return categoryListRepository
      .fetchCategories()
      .flowOn(dispatcher.io)
  }
}
