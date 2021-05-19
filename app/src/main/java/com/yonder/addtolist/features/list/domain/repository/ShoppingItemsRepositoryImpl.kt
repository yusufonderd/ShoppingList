package com.yonder.addtolist.features.list.domain.repository

import com.yonder.addtolist.core.base.BaseResponse
import com.yonder.addtolist.features.list.data.local.datasource.ShoppingItemsDataSource
import com.yonder.addtolist.features.list.data.remote.datasource.ShoppingItemsRemoteDataSource
import com.yonder.addtolist.features.list.data.remote.response.CategoryProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class ShoppingItemsRepositoryImpl @Inject constructor(
  private val remoteDataSource: ShoppingItemsRemoteDataSource,
  private val localDataSource : ShoppingItemsDataSource
) : ShoppingItemsRepository {

  override suspend fun fetchShoppingItems(languageId: Int?): BaseResponse<ArrayList<CategoryProductResponse>> {

      return remoteDataSource.fetchShoppingItems(languageId)
  }
}