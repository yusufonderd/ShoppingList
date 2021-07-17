package com.yonder.addtolist.features.list.data

import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.features.list.data.local.datasource.UserListDataSource
import com.yonder.addtolist.features.list.data.local.entity.UserListEntity
import com.yonder.addtolist.features.list.data.remote.datasource.ShoppingItemsRemoteDataSource
import com.yonder.addtolist.features.list.domain.mapper.UserListMapper
import com.yonder.addtolist.features.list.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class UserListRepositoryImpl @Inject constructor(
  private val remoteDataSource: ShoppingItemsRemoteDataSource,
  private val localDataSource: UserListDataSource,
  private val mapper: UserListMapper
) : UserListRepository {

  override fun getUserList(): Flow<Result<List<UserListEntity>>> = flow {
    emit(Result.Loading)

    remoteDataSource.fetch().let { response ->
      if (response.success == true) {
        val list = ListMapperImpl(mapper).map(response.data.orEmpty())
        localDataSource.insertAll(list)
      }
    }

    emit(Result.Success(localDataSource.getUserLists()))

    /* ListMapperImpl(entityMapper)
       .map(localDataSource.getUserLists()).run {
         emit(Result.Success(this))
       }

     ListMapperImpl(responseMapper)
       .map(remoteDataSource.fetch().data.orEmpty()).run {
         emit(Result.Success(this))
       }*/
  }

}

