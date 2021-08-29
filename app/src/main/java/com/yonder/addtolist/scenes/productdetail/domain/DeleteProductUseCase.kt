package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.UserListProductEntity
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */
class DeleteProductUseCaseImpl @Inject constructor(private val appDatabase: AppDatabase) :
  DeleteProductUseCase {
  override suspend operator fun invoke(product: UserListProductEntity) {
    appDatabase.userListProductDao().delete(product)
  }
}

interface DeleteProductUseCase {
  suspend operator fun invoke(product: UserListProductEntity)
}