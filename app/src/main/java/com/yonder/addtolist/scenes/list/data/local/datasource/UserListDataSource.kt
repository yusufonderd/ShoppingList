package com.yonder.addtolist.scenes.list.data.local.datasource

import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * Yusuf Onder on 12,May,2021
 */

interface UserListDataSource {
  suspend fun insert(userList: UserListEntity)
  suspend fun insertAll(list: List<UserListEntity>)
  suspend fun getUserLists(): List<UserListEntity>
  suspend fun insertProducts(products: List<UserListProductEntity>)

}