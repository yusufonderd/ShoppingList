package com.yonder.addtolist.scenes.detail.domain.product

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
interface ProductUseCase {
  fun addProduct(
    listId: String,
    listUUID: String,
    productName: String,
    productCategoryImage: String
  ): Flow<Result<UserListProductEntity>>

  fun getProductEntityForName(name: String) : Flow<ProductEntity?>
  fun removeProduct(productEntity: UserListProductEntity): Flow<Result<UserListProductEntity>>
  fun updateProduct(listId: String,productEntity: UserListProductEntity): Flow<Result<UserListProductEntity>>
}
