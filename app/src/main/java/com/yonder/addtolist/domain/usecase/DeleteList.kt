package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.datasource.UserListDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
/*
class DeleteList @Inject constructor(
    var userListDataSource: UserListDataSource,
    private val dispatcher: CoroutineThread
) {
    operator suspend fun invoke(uuid: String,coroutineScope: CoroutineScope){
        userListDataSource.getUserListByUUID(uuid).collectLatest {
            it.

        }
    }
}*/