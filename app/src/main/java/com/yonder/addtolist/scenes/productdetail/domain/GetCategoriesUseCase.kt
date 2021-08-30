package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.scenes.productdetail.domain.mapper.CategoryEntityToUiModelMapper
import com.yonder.addtolist.scenes.home.domain.model.CategoryUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

class GetCategoriesUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase,
  private val dispatcher: CoroutineThread,
  private val userPreferenceDataStore: UserPreferenceDataStore,
  private val mapper: CategoryEntityToUiModelMapper
) : GetCategoriesUseCase {

  override operator fun invoke(): Flow<List<CategoryUiModel>> {
    return appDatabase.categoryDao()
      .findByLanguageId(userPreferenceDataStore.getAppLanguageId())
      .map { ListMapperImpl(mapper).map(it) }
      .flowOn(dispatcher.io)
  }

}

interface GetCategoriesUseCase {
  operator fun invoke(): Flow<List<CategoryUiModel>>
}

