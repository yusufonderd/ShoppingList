package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.domain.uimodel.ProductEntitySummaryToUiModel
import com.yonder.addtolist.local.AppDatabase
import com.yonder.core.base.mapper.ListMapperImpl
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularProducts @Inject constructor(
    private val appDatabase: AppDatabase,
    private val userPreferenceDataStore: UserPreferenceDataStore,
    private val mapper: ProductEntitySummaryToUiModel,
    private val dispatcher: CoroutineThread,
) {

     operator fun invoke() = flow {
        val popularProducts =
            appDatabase.productDao().getPopularProducts(userPreferenceDataStore.getAppLanguageId())
        emit(ListMapperImpl(mapper).map(popularProducts))
    }.flowOn(dispatcher.io)
}