package com.yonder.addtolist.scenes.list.domain.usecase

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.scenes.list.data.remote.input.CreateUserListRequest
import com.yonder.addtolist.scenes.list.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
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

  override fun createList(listName: String, listColor: String): Flow<Result<UserListEntity>> {
    return repository
      .createUserList(CreateUserListRequest(listName, listColor, UUID.randomUUID().toString()))
      .flowOn(dispatcher.io)
  }

}
