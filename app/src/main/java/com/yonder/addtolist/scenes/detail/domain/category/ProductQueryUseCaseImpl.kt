package com.yonder.addtolist.scenes.detail.domain.category

import com.yonder.addtolist.core.network.thread.CoroutineThread
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
  private val dispatcher: CoroutineThread
) : ProductQueryUseCase {

  override fun fetchProductByQuery(query: String, limit: Int) = flow {
    val popularProducts = appDatabase.productDao().fetchProducts(1, query, limit)
    emit(popularProducts)
  }.flowOn(dispatcher.io)

  override fun fetchPopularProducts() = flow {
    emit(appDatabase.productDao().getPopularProducts(1))
  }.flowOn(dispatcher.io)

}