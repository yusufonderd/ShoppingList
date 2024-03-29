package com.yonder.addtolist.data.datasource

import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface UserListDataSource {
  suspend fun delete(userList: UserListEntity)
  suspend fun insert(userList: UserListEntity)
  suspend fun insertAll(list: List<UserListEntity>)
  suspend fun getUserLists(): List<UserListEntity>
  suspend fun getUserListWithProducts(): List<UserListWithProducts>
  suspend fun getFirstUserListWithProducts(listUUID: String): UserListWithProducts?
  fun getUserListByUUID(listUUID: String): Flow<UserListWithProducts>
  suspend fun getUserList(listUUID: String): UserListEntity?
  suspend fun insertProducts(products: List<UserListProductEntity>)
  suspend fun findProduct(listUUID: String, productName: String): UserListProductEntity
}
