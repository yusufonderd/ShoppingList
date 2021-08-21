package com.yonder.addtolist.scenes.home.domain.usecase

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */

interface LocalListUseCase {
  fun getUserListByUUID(listUUID: String): Flow<UserListWithProducts>
}

class LocalListUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase
) : LocalListUseCase {
  override fun getUserListByUUID(listUUID: String): Flow<UserListWithProducts> {
    return appDatabase.userListProductDao().getUserListWithProductsBy(listUUID)
  }
}

interface LocalUserListProductUseCase {
  fun getProduct(listUUID: String, productName: String): Flow<UserListProductEntity?>
}

class LocalUserListProductUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase
) : LocalUserListProductUseCase {
  override fun getProduct(listUUID: String, productName: String): Flow<UserListProductEntity?> {
    return appDatabase.userListProductDao().findByListUUID(listUUID, productName)
  }
}