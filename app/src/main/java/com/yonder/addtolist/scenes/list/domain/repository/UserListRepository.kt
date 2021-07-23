package com.yonder.addtolist.scenes.list.domain.repository

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.local.entity.UserListWithProducts
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface UserListRepository {
  fun getUserLists(): Flow<Result<List<UserListWithProducts>>>
  fun createUserList(request: CreateUserListRequest): Flow<Result<UserListEntity>>
}