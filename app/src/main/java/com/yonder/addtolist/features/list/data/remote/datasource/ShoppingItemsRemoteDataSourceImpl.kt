package com.yonder.addtolist.features.list.data.remote.datasource

import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.features.list.data.remote.ShoppingListApiService
import com.yonder.addtolist.features.list.data.remote.response.UserListResponse
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class ShoppingItemsRemoteDataSourceImpl @Inject constructor(private val api: ShoppingListApiService) :
  ShoppingItemsRemoteDataSource {

  override suspend fun fetch(): BaseResponse<List<UserListResponse>> {
    return api.fetchUserLists()
  }
}