package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.domain.mapper.UserListWithProductsMapper
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

class GetUserList @Inject constructor(
    private val appDatabase: AppDatabase,
    private val mapper: UserListWithProductsMapper
) {
    operator fun invoke(uuid: String): Flow<UserListUiModel> {
        val userListWithProducts = appDatabase.userListProductDao().getUserListWithProductsBy(uuid)
        return userListWithProducts.mapNotNull {
            mapper.map(it)
        }
    }
}

