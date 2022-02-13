package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.domain.mapper.UserListWithProductsMapper
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

class GetUserList @Inject constructor(
    private val appDatabase: AppDatabase,
    private val mapper: UserListWithProductsMapper
) {
    operator fun invoke(input: String): Flow<UserListUiModel> {
        val userListWithProducts = appDatabase.userListProductDao().getUserListWithProductsBy(input)
        return userListWithProducts.map {
            mapper.map(it)
        }
    }
}

