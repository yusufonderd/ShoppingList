package com.yonder.addtolist.data.repository

import com.yonder.addtolist.core.network.exceptions.ServerResultException
import com.yonder.core.base.mapper.ListMapperImpl
import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.data.datasource.UserListDataSource
import com.yonder.addtolist.network.ApiService
import com.yonder.addtolist.data.remote.response.UserListResponse
import com.yonder.addtolist.domain.mapper.UserListProductMapper
import com.yonder.addtolist.domain.mapper.UserListRequestMapper
import com.yonder.addtolist.domain.mapper.UserListResponseToEntityMapper
import com.yonder.core.network.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
  private val service: ApiService,
  private val localDataSource: UserListDataSource,
  private val userListRequestToEntityMapper: UserListRequestMapper,
  private val responseToEntityMapper: UserListResponseToEntityMapper
) : UserListRepository {

  override fun createUserList(request: CreateUserListRequest): Flow<RestResult<UserListEntity>> = flow {
    val response = service.createUserList(request)
    val userListId = response.data?.id
    val userListEntity = userListRequestToEntityMapper.map(request)
    if (response.success == true && userListId != null) {
      userListEntity.id = userListId
      userListEntity.sync = true
      localDataSource.insert(userListEntity)
      emit(RestResult.Success(userListEntity))
    }else{
      emit(RestResult.Error(ServerResultException()))
    }
  }.catch { exception ->
    exception.printStackTrace()
    emit(RestResult.Error(exception))
  }

  private suspend fun insertLists(list: List<UserListResponse>) {
    localDataSource.insertAll(
      ListMapperImpl(responseToEntityMapper)
        .map(list)
    )
  }

  private suspend fun insertProducts(list: List<UserListResponse>) {
    list.forEach { userList ->
      val productEntities =
        ListMapperImpl(
          UserListProductMapper(userList.uuid)
        ).map(userList.userListProducts)
      localDataSource.insertProducts(productEntities)
    }
  }

  override fun getUserLists(): Flow<List<UserListWithProducts>> = flow {
    val localUserLists = localDataSource.getUserListWithProducts()
   val productCount =  localUserLists.flatMap { it.products }.size
    //if (localUserLists.isEmpty()) {
      val response = service.getUserLists()
      val userListsResponse: List<UserListResponse> = response.data.orEmpty()
      if (response.success == true) {
        insertLists(userListsResponse)
        insertProducts(userListsResponse)
      }
    //}
    emit(localDataSource.getUserListWithProducts())
  }.catch {
    emit(localDataSource.getUserListWithProducts())
  }

}

