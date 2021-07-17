package com.yonder.addtolist.features.list.data.local.datasource

import com.yonder.addtolist.data.remote.local.AppDatabase
import com.yonder.addtolist.features.list.data.local.entity.UserListEntity
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class UserListDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
  UserListDataSource {

  override suspend fun insert(userList: UserListEntity) {
    return appDatabase.userListDao().insert(userList)
  }

  override suspend fun insertAll(list: List<UserListEntity>) {
    return appDatabase.userListDao().insert(list)
  }

  override suspend fun getUserLists(): List<UserListEntity> {
    return appDatabase.userListDao().getUserLists()
  }

}