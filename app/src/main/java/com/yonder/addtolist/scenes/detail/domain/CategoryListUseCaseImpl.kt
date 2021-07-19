package com.yonder.addtolist.scenes.detail.domain

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.CategoryWithProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
class CategoryListUseCaseImpl @Inject constructor(
  private val categoryListRepository: CategoryListRepository,
  private val dispatcher: CoroutineThread
) : CategoryListUseCase {

  override fun getCategories(): Flow<Result<List<CategoryWithProducts>>> {
    return categoryListRepository
      .fetchCategories()
      .flowOn(dispatcher.io)
  }
}