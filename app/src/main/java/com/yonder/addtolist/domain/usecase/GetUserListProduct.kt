package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.base.UseCase
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
 * Created on 12.01.2022
 */

class GetUserListProduct @Inject constructor(
    private val appDatabase: AppDatabase,
    private val mapper: UserListProductEntityToUiModel,
    private val dispatcher: CoroutineThread
)  {

      operator fun invoke(input: Int): Flow<UserListProductUiModel?> {
        return appDatabase.userListProductDao()
            .findById(input)
            .map { mapper.map(it) }
            .flowOn(dispatcher.io)
    }
}