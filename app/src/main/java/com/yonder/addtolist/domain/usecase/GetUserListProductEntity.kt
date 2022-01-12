package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.base.UseCaseNonFlow
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.UserListProductEntity
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

class GetUserListProductEntity @Inject constructor(
    private val appDatabase: AppDatabase
) : UseCaseNonFlow<GetUserListProductEntity.Param, UserListProductEntity?> {

    override suspend fun invoke(input: Param): UserListProductEntity? {
        return appDatabase.userListProductDao().findByListUUID(input.listUUID, input.productName)
    }

    data class Param(val listUUID: String, val productName: String)
}