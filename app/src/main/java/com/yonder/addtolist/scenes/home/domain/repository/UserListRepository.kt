package com.yonder.addtolist.scenes.home.domain.repository

import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.core.network.RestResult
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface UserListRepository {
  fun getUserLists(): Flow<List<UserListWithProducts>>
  fun createUserList(request: CreateUserListRequest): Flow<RestResult<UserListEntity>>
}
