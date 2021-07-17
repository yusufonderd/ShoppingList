package com.yonder.addtolist.features.list.domain.repository

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.features.list.data.local.entity.UserListEntity
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface UserListRepository {
  fun getUserList(): Flow<Result<List<UserListEntity>>>
}