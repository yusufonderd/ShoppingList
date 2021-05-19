package com.yonder.addtolist.features.list.domain.usecase

import com.yonder.addtolist.core.NetworkResult
import com.yonder.addtolist.data.di.thread.CoroutineThread
import com.yonder.addtolist.features.list.domain.mapper.CategoryProductsMapper
import com.yonder.addtolist.features.list.domain.model.CategoryProductsUiModel
import com.yonder.addtolist.features.list.domain.repository.ShoppingItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class ShoppingItemsUseCaseImpl @Inject constructor(
  private val repository: ShoppingItemsRepository,
  private val mapper: CategoryProductsMapper,
  private val dispatcher: CoroutineThread

) : ShoppingItemsUseCase {

  override fun fetchShoppingItems(languageId: Int?): Flow<NetworkResult<CategoryProductsUiModel>> {
    return flow {
      emit(NetworkResult.Loading)
      val shoppingItems = repository.fetchShoppingItems(languageId)
      emit(NetworkResult.Success(mapper.map(shoppingItems)))
    }.catch { error ->
      emit(NetworkResult.Error(error))
    }.flowOn(dispatcher.io)
  }
}