package com.yonder.addtolist.features.list.domain.usecase


import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.features.list.data.local.entity.UserListEntity
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface UserListUseCase {
  fun getUserList(): Flow<Result<List<UserListEntity>>>
}