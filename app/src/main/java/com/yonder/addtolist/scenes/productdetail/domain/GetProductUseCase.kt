package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.UserListProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

class GetProductUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase,
  private val dispatcher: CoroutineThread,

  ) : GetProductUseCase{
   override operator fun invoke(id: Int): Flow<UserListProductEntity> {
    return appDatabase.userListProductDao().findById(id).flowOn(dispatcher.io)
  }
}

interface GetProductUseCase{
  operator fun invoke(id: Int) : Flow<UserListProductEntity>
}
