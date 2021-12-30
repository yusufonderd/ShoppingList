package com.yonder.addtolist.scenes.home.data

import com.yonder.core.base.mapper.ListMapperImpl
import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.home.data.local.datasource.UserListDataSource
import com.yonder.addtolist.scenes.home.data.remote.ApiService
import com.yonder.addtolist.scenes.home.data.remote.response.UserListResponse
import com.yonder.addtolist.scenes.home.domain.mapper.UserListProductMapper
import com.yonder.addtolist.scenes.home.domain.mapper.UserListRequestMapper
import com.yonder.addtolist.scenes.home.domain.mapper.UserListResponseToEntityMapper
import com.yonder.addtolist.scenes.home.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class UserListRepositoryImpl @Inject constructor(
  private val service: ApiService,
  private val localDataSource: UserListDataSource,
  private val userListRequestToEntityMapper: UserListRequestMapper,
  private val responseToEntityMapper: UserListResponseToEntityMapper
) : UserListRepository {

  override fun createUserList(request: CreateUserListRequest): Flow<UserListEntity> = flow {
    val response = service.createUserList(request)
    val userListEntity = userListRequestToEntityMapper.map(request)
    val userListResponse = response.data
    if (response.success == true && userListResponse?.id != null) {
      userListEntity.id = userListResponse.id
      userListEntity.sync = true
    }
    localDataSource.insert(userListEntity)
    emit(userListEntity)
  }.catch { exception ->
    Timber.d("EEE => ${exception.localizedMessage}")
    exception.printStackTrace()
    val userListEntity = userListRequestToEntityMapper.map(request)
    localDataSource.insert(userListEntity)
    emit(userListEntity)
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
    if (localUserLists.isEmpty()) {
      val response = service.getUserLists()
      val userListsResponse: List<UserListResponse> = response.data.orEmpty()
      if (response.success == true) {
        insertLists(userListsResponse)
        insertProducts(userListsResponse)
      }
    }
    emit(localDataSource.getUserListWithProducts())
  }.catch {
    emit(localDataSource.getUserListWithProducts())
  }

}

