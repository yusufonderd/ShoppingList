package com.yonder.addtolist.scenes.home.domain.usecase

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.UserListWithProducts
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */

interface GetUserListUseCase {
  operator fun invoke(listUUID: String): Flow<UserListWithProducts>
}

class GetUserListUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase
) : GetUserListUseCase {
  override fun invoke(listUUID: String): Flow<UserListWithProducts> {
    return appDatabase.userListProductDao().getUserListWithProductsBy(listUUID)
  }
}

