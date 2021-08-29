package com.yonder.addtolist.scenes.listdetail.domain

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.listdetail.domain.mapper.UserListProductMapper
import com.yonder.addtolist.scenes.listdetail.domain.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 29.08.2021
 */

interface AddProductUseCase {
  suspend operator fun invoke(
    listId: String?,
    listUUID: String,
    productName: String,
    productCategoryImage: String
  ): Flow<Result<UserListProductEntity>>
}


class AddProductUseCaseImpl @Inject constructor(
  private val productRepository: ProductRepository,
  private val dispatcher: CoroutineThread
):
  AddProductUseCase {
  override suspend fun invoke(
    listId: String?,
    listUUID: String,
    productName: String,
    productCategoryImage: String
  ): Flow<Result<UserListProductEntity>> {
    val createUserListProductRequest =
      UserListProductMapper.mapProductEntitySummaryToRequest(
        listId,
        productName,
        productCategoryImage
      )
    return productRepository
      .addProduct(listUUID, createUserListProductRequest)
      .flowOn(dispatcher.io)
  }
}
