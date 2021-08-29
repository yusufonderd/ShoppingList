package com.yonder.addtolist.scenes.home.domain.usecase

import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.home.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 29.08.2021
 */
interface CreateListUseCase {
  suspend operator fun invoke(listName: String, listColor: String): Flow<Result<UserListEntity>>
}

class CreateListUseCaseImpl @Inject constructor(
  private val repository: UserListRepository,
  private val dispatcher: CoroutineThread
) : CreateListUseCase {

  override suspend fun invoke(listName: String, listColor: String): Flow<Result<UserListEntity>> {
    return repository
      .createUserList(CreateUserListRequest(listName, listColor, UUID.randomUUID().toString()))
      .flowOn(dispatcher.io)
  }


}
