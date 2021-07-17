package com.yonder.addtolist.features.list.data.remote.datasource

import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.features.list.data.remote.response.UserListResponse

/**
 * Yusuf Onder on 12,May,2021
 */

interface ShoppingItemsRemoteDataSource {
  suspend fun fetch(): BaseResponse<List<UserListResponse>>
}