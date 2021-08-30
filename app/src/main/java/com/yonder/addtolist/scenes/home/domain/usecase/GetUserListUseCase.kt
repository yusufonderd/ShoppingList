package com.yonder.addtolist.scenes.home.domain.usecase

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.scenes.home.domain.mapper.UserListWithProductsMapper
import com.yonder.addtolist.scenes.home.domain.model.UserListUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */

interface GetUserListUseCase {
  operator fun invoke(listUUID: String): Flow<UserListUiModel>
}

class GetUserListUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase,
  private val mapper: UserListWithProductsMapper
) : GetUserListUseCase {
  override fun invoke(listUUID: String): Flow<UserListUiModel> {
    val userListWithProducts = appDatabase.userListProductDao().getUserListWithProductsBy(listUUID)
    return userListWithProducts.map {
      mapper.map(it)
    }
  }
}

