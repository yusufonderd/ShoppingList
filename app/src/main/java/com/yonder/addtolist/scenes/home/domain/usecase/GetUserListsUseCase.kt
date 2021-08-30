package com.yonder.addtolist.scenes.home.domain.usecase

import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.scenes.home.domain.mapper.UserListWithProductsMapper
import com.yonder.addtolist.scenes.home.domain.model.UserListUiModel
import com.yonder.addtolist.scenes.home.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 29.08.2021
 */

class GetUserListsUseCaseImpl @Inject constructor(
  private val repository: UserListRepository,
  private val dispatcher: CoroutineThread,
  private val mapper : UserListWithProductsMapper,
) : GetUserListsUseCase {
  override suspend fun invoke(): Flow<List<UserListUiModel>> {
    return repository
      .getUserLists()
      .map { ListMapperImpl(mapper).map(it) }
      .flowOn(dispatcher.io)
  }
}

interface GetUserListsUseCase {
  suspend operator fun invoke(): Flow<List<UserListUiModel>>
}
