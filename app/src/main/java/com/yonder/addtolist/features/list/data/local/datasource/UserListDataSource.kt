package com.yonder.addtolist.features.list.data.local.datasource

import com.yonder.addtolist.features.list.data.local.entity.UserListEntity

/**
 * Yusuf Onder on 12,May,2021
 */

interface UserListDataSource {
  suspend fun insert(userList: UserListEntity)
  suspend fun insertAll(list: List<UserListEntity>)
  suspend fun getUserLists(): List<UserListEntity>
}