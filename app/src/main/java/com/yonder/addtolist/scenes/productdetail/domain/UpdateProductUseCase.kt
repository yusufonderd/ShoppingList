package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.UserListProductEntity
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

class UpdateProductUseCaseImpl @Inject constructor(private val appDatabase: AppDatabase): UpdateProductUseCase {
  override suspend operator fun invoke(product: UserListProductEntity) {
    appDatabase.userListProductDao().update(product)
  }
}

interface UpdateProductUseCase{
  suspend operator fun invoke(product: UserListProductEntity)
}