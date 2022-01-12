package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.base.NoInputUseCase
import com.yonder.addtolist.core.base.UseCase
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.scenes.home.domain.model.CategoryUiModel
import com.yonder.addtolist.scenes.productdetail.domain.mapper.CategoryEntityToUiModelMapper
import com.yonder.core.base.mapper.ListMapperImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */


class GetCategories @Inject constructor(
    private val appDatabase: AppDatabase,
    private val dispatcher: CoroutineThread,
    private val userPreferenceDataStore: UserPreferenceDataStore,
    private val mapper: CategoryEntityToUiModelMapper
)  {

    operator fun invoke(): Flow<List<CategoryUiModel>> {
        return appDatabase.categoryDao()
            .findByLanguageId(userPreferenceDataStore.getAppLanguageId())
            .map { ListMapperImpl(mapper).map(it) }
            .flowOn(dispatcher.io)
    }

}