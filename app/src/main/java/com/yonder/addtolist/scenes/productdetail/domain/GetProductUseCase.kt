package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.scenes.home.domain.mapper.UserListProductEntityToUiModel
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

class GetProductUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase,
  private val mapper: UserListProductEntityToUiModel,
  private val dispatcher: CoroutineThread
) : GetProductUseCase {

  override operator fun invoke(id: Int): Flow<UserListProductUiModel?> {
    return appDatabase.userListProductDao()
      .findById(id)
      .map { mapper.map(it) }
      .flowOn(dispatcher.io)
  }
}

interface GetProductUseCase {
  operator fun invoke(id: Int): Flow<UserListProductUiModel?>
}
