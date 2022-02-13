package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.base.UseCase
import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.data.repository.UserListRepository
import com.yonder.core.network.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

class CreateList @Inject constructor(
    private val repository: UserListRepository,
    private val dispatcher: CoroutineThread
) : UseCase<CreateList.Params, RestResult<UserListEntity>> {

    override suspend fun invoke(input: Params): Flow<RestResult<UserListEntity>> {
        val listUUID = UUID.randomUUID().toString()
        val createUserRequest = CreateUserListRequest(
            name = input.listName,
            color = input.listColor,
            uuid = listUUID
        )
        return repository
            .createUserList(createUserRequest)
            .flowOn(dispatcher.io)
    }

    data class Params(var listName: String, var listColor: String)

}