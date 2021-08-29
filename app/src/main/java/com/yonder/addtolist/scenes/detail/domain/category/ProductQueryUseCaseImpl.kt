package com.yonder.addtolist.scenes.detail.domain.category

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.AppDatabase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

class ProductQueryUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase,
  private val userPreferenceDataStore: UserPreferenceDataStore,
  private val dispatcher: CoroutineThread
) : ProductQueryUseCase {

  private fun getAppLanguageId() = userPreferenceDataStore.getAppLanguageId()

  override fun fetchProductByQuery(query: String, limit: Int) = flow {
    val products = appDatabase.productDao()
      .fetchProducts(getAppLanguageId(), query, limit)
    emit(products)
  }.flowOn(dispatcher.io)


  override fun fetchPopularProducts() = flow {
    val popularProducts = appDatabase.productDao()
      .getPopularProducts(getAppLanguageId())
    emit(popularProducts)
  }.flowOn(dispatcher.io)

}
