package com.yonder.addtolist.scenes.list.data

import com.yonder.addtolist.core.data.BaseRepository
import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.core.network.exceptions.ServerResultException
import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.scenes.list.data.local.datasource.UserListDataSource
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.scenes.list.data.remote.ShoppingListApiService
import com.yonder.addtolist.scenes.list.data.remote.input.CreateUserListRequest
import com.yonder.addtolist.scenes.list.domain.mapper.UserListMapper
import com.yonder.addtolist.scenes.list.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class UserListRepositoryImpl @Inject constructor(
  private val apiService: ShoppingListApiService,
  private val localDataSource: UserListDataSource,
  private val mapper: UserListMapper
) : BaseRepository(), UserListRepository {

  override fun createUserList(request: CreateUserListRequest): Flow<Result<UserListEntity>> = flow {
    emit(Result.Loading)
    val response = apiService.createUserList(request)
    if (response.success == true) {
      val localUserList = mapper.map(response.data)
      localDataSource.insert(localUserList)
      emit(Result.Success(localUserList))
    } else {
      emit(Result.Error<UserListEntity>(ServerResultException()))
    }
  }.catch { error ->
    error.printStackTrace()
    emit(Result.Error<UserListEntity>(error))
  }


  override fun getUserList(): Flow<Result<List<UserListEntity>>> = flow {
    emit(Result.Loading)
    var localUserLists = localDataSource.getUserLists()
    if (localUserLists.isNotEmpty()) {
      emit(Result.Success(localUserLists))
    }
    val response = apiService.getUserLists()
    if (response.success == true) {
      if (localUserLists.isEmpty()) {
        localUserLists = ListMapperImpl(mapper).map(response.data.orEmpty())
        localDataSource.insertAll(localUserLists)
      }
      emit(Result.Success(localUserLists))
    } else {
      emit(Result.Error<List<UserListEntity>>(ServerResultException()))
    }
  }.catch { error ->
    error.printStackTrace()
    emit(Result.Error<List<UserListEntity>>(error))
  }

}

