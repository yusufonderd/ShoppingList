package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.domain.uimodel.ProductEntitySummaryToUiModel
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.core.base.mapper.ListMapperImpl
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetProductByQuery @Inject constructor(
    private val appDatabase: AppDatabase,
    private val userPreferenceDataStore: UserPreferenceDataStore,
    private val mapper: ProductEntitySummaryToUiModel,
    private val dispatcher: CoroutineThread,
) {
    operator fun invoke(query: String, limit: Int) = flow {
        val products = appDatabase.productDao()
            .fetchProducts(
                languageId = userPreferenceDataStore.getAppLanguageId(),
                query = query,
                limit = limit
            )
        val list: ArrayList<ProductEntitySummary> = arrayListOf(ProductEntitySummary(query, ""))
        list.addAll(products)
        emit(ListMapperImpl(mapper).map(list))
    }.flowOn(dispatcher.io)

}