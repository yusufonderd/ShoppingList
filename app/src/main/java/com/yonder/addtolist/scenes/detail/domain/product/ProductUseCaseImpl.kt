package com.yonder.addtolist.scenes.detail.domain.product

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.detail.domain.mapper.UserListProductMapper
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

  override fun addProduct(
    listId: String,
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