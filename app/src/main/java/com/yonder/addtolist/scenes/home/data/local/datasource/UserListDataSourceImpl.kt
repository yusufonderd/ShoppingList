package com.yonder.addtolist.scenes.home.data.local.datasource

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class UserListDataSourceImpl @Inject constructor(private val appDatabase: com.yonder.addtolist.local.AppDatabase) :
  UserListDataSource {

  override suspend fun insert(userList: UserListEntity) {
    return appDatabase.userListDao().insert(userList)
  }

  override suspend fun insertAll(list: List<UserListEntity>) {
    return appDatabase.userListDao().insert(list)
  }

  override suspend fun insertProducts(products: List<UserListProductEntity>) {
    return appDatabase.userListProductDao().insertAll(products)
  }

  override suspend fun getUserLists(): List<UserListEntity> {
    return appDatabase.userListDao().getUserLists()
  }

  override suspend fun getUserListWithProducts(): List<UserListWithProducts> {
    return appDatabase.userListProductDao().getAllUserListWithProducts()
  }

  override  fun getUserListByUUID(listUUID: String): Flow<UserListWithProducts> {
    return appDatabase.userListProductDao().getUserListWithProductsBy(listUUID)
  }
}
