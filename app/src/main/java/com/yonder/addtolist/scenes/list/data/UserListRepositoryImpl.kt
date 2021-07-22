package com.yonder.addtolist.scenes.list.data

import com.yonder.addtolist.core.data.BaseRepository
import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.core.network.exceptions.ServerResultException
import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.scenes.list.data.local.datasource.UserListDataSource
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.scenes.list.data.remote.ShoppingListApiService
import com.yonder.addtolist.scenes.list.data.remote.input.CreateUserListRequest
import com.yonder.addtolist.scenes.list.data.remote.response.UserListResponse
import com.yonder.addtolist.scenes.list.domain.mapper.UserListMapper
import com.yonder.addtolist.scenes.list.domain.mapper.UserListProductMapper
import com.yonder.addtolist.scenes.list.domain.mapper.UserListRequestMapper
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
) : UserListRepository {

  override fun createUserList(request: CreateUserListRequest): Flow<Result<UserListEntity>> = flow {
    emit(Result.Loading)
    val response = apiService.createUserList(request)
    val entity = UserListRequestMapper().map(request)
    if (response.success == true) {
      entity.id = response.data?.id
      emit(Result.Success(entity))
    } else {
      emit(Result.Error<UserListEntity>(ServerResultException()))
    }
    localDataSource.insert(entity)
  }.catch { error ->
    localDataSource.insert(UserListRequestMapper().map(request))
    error.printStackTrace()
    emit(Result.Error<UserListEntity>(error))
  }


  private suspend fun insertLists(list: List<UserListResponse>?) {
    localDataSource.insertAll(
      ListMapperImpl(mapper)
        .map(list.orEmpty())
    )
  }

  private suspend fun insertProducts(list: List<UserListResponse>?) {
    list?.forEach { userList ->
      val productEntities =
        ListMapperImpl(
          UserListProductMapper(userList.uuid)
        ).map(userList.userListProducts)
      localDataSource.insertProducts(productEntities)
    }
  }

  override fun getUserList(): Flow<Result<List<UserListEntity>>> = flow {
    emit(Result.Loading)
    val localUserLists = localDataSource.getUserLists()
    if (localUserLists.isNotEmpty()) {
      emit(Result.Success(localUserLists))
    }
    val response = apiService.getUserLists()
    if (response.success == true) {
      if (localUserLists.isEmpty()) {
        val userLists = response.data
        insertLists(userLists)
        insertProducts(userLists)
      }
      emit(Result.Success(localDataSource.getUserLists()))
    } else {
      emit(Result.Error<List<UserListEntity>>(ServerResultException()))
    }
  }.catch { error ->
    error.printStackTrace()
    emit(Result.Error<List<UserListEntity>>(error))
  }


}

