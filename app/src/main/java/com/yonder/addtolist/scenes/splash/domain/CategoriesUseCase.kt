package com.yonder.addtolist.scenes.splash.domain

import com.yonder.addtolist.core.network.responses.Result
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
  fun getCategories(): Flow<Result<List<CategoryWithProducts>>>
}

class CategoriesUseCaseImpl @Inject constructor(
  private val categoryListRepository: CategoryListRepository,
  private val dispatcher: CoroutineThread
) : CategoriesUseCase {
  override fun getCategories(): Flow<Result<List<CategoryWithProducts>>> {
    return categoryListRepository
      .fetchCategories()
      .flowOn(dispatcher.io)
  }
}
