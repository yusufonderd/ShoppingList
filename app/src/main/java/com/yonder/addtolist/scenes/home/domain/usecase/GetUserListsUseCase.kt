package com.yonder.addtolist.scenes.home.domain.usecase

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.home.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 29.08.2021
 */

class GetUserListsUseCaseImpl @Inject constructor(
  private val repository: UserListRepository,
  private val dispatcher: CoroutineThread
) : GetUserListsUseCase {
  override suspend fun invoke(): Flow<List<UserListWithProducts>> {
    return repository
      .getUserLists()
      .flowOn(dispatcher.io)
  }
}

interface GetUserListsUseCase {
  suspend operator fun invoke(): Flow<List<UserListWithProducts>>
}