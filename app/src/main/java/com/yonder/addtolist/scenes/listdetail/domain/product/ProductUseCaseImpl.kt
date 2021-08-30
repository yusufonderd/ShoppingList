package com.yonder.addtolist.scenes.listdetail.domain.product

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
class ProductUseCaseImpl @Inject constructor(
  private val productRepository: ProductRepository,
  private val dispatcher: CoroutineThread
) : ProductUseCase {

  override fun getProductEntityForName(name: String): Flow<ProductEntity?> {
    return productRepository.getProductEntityByName(name)
      .flowOn(dispatcher.io)
  }



  override fun removeProduct(
    productEntity: UserListProductEntity
  ): Flow<Result<UserListProductEntity>> {
    return productRepository
      .removeProduct(productEntity)
      .flowOn(dispatcher.io)
  }

  override fun updateProduct(
    listId: String,
    productEntity: UserListProductEntity
  ): Flow<Result<UserListProductEntity>> {
    return productRepository
      .updateProduct(listId, productEntity)
      .flowOn(dispatcher.io)
  }

}
