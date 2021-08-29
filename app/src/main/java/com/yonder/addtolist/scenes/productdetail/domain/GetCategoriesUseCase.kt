package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

class GetCategoriesUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase,
  private val dispatcher: CoroutineThread,
  private val userPreferenceDataStore: UserPreferenceDataStore,
) : GetCategoriesUseCase {

  override operator fun invoke(): Flow<List<CategoryEntity>> {
    return appDatabase.categoryDao()
      .findByLanguageId(userPreferenceDataStore.getAppLanguageId())
      .flowOn(dispatcher.io)
  }

}

interface GetCategoriesUseCase {
  operator fun invoke(): Flow<List<CategoryEntity>>
}

