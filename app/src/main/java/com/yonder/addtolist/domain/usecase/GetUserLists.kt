package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.base.NoInputUseCase
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.scenes.home.domain.mapper.UserListWithProductsMapper
import com.yonder.addtolist.scenes.home.domain.model.UserListUiModel
import com.yonder.addtolist.scenes.home.domain.repository.UserListRepository
import com.yonder.core.base.mapper.ListMapperImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */
class GetUserLists @Inject constructor(
    private val repository: UserListRepository,
    private val dispatcher: CoroutineThread,
    private val mapper : UserListWithProductsMapper,
) : NoInputUseCase<List<UserListUiModel>> {
    override suspend fun invoke(): Flow<List<UserListUiModel>> {
        return repository
            .getUserLists()
            .map { ListMapperImpl(mapper).map(it) }
            .flowOn(dispatcher.io)
    }
}