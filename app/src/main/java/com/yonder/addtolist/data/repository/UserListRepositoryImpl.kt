package com.yonder.addtolist.data.repository

import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.data.datasource.UserListDataSource
import com.yonder.addtolist.domain.mapper.UserListRequestMapper
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.core.network.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

interface UserListRepository {
  fun getUserLists(): Flow<List<UserListWithProducts>>
  fun createUserList(request: CreateUserListRequest): Flow<RestResult<UserListEntity>>
}

class UserListRepositoryImpl @Inject constructor(
  private val localDataSource: UserListDataSource,
  private val userListRequestToEntityMapper: UserListRequestMapper
) : UserListRepository {

  override fun createUserList(request: CreateUserListRequest): Flow<RestResult<UserListEntity>> = flow {
    val userListEntity = userListRequestToEntityMapper.map(request)
    localDataSource.insert(userListEntity)
    emit(RestResult.Success(userListEntity))
  }

  override fun getUserLists(): Flow<List<UserListWithProducts>> = flow {
    emit(localDataSource.getUserListWithProducts())
  }
}

