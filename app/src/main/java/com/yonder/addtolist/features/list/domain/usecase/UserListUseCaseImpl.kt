package com.yonder.addtolist.features.list.domain.usecase

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.features.list.data.local.entity.UserListEntity
import com.yonder.addtolist.features.list.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class UserListUseCaseImpl @Inject constructor(
  private val repository: UserListRepository,
  private val dispatcher: CoroutineThread
) : UserListUseCase {

  override fun getUserList(): Flow<Result<List<UserListEntity>>> {
    return repository
      .getUserList()
      .flowOn(dispatcher.io)
  }

}
