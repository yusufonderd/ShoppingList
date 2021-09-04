package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.domain.product.ProductRepository
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

class DeleteProductUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase,
   private val productRepository: ProductRepository
) :
  DeleteProductUseCase {
  override suspend fun invoke(product: UserListProductUiModel) {
    val productEntity = appDatabase.userListProductDao()
      .findByListUUID(listUUID = product.listUUID, productName = product.name)
    appDatabase.userListProductDao().delete(productEntity)
  }

}

interface DeleteProductUseCase {
  suspend operator fun  invoke(product: UserListProductUiModel)
}


