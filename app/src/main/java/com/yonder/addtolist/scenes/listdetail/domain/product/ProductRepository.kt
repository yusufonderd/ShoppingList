package com.yonder.addtolist.scenes.listdetail.domain.product

import com.yonder.addtolist.core.network.request.CreateUserListProductRequest
import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
interface ProductRepository {
  fun addProduct(
    listUUID: String,
    product: CreateUserListProductRequest
  ): Flow<Result<UserListProductEntity>>

  suspend fun delete(product: UserListProductUiModel)
  fun updateProduct(
    listId: String,
    product: UserListProductEntity
  ): Flow<Result<UserListProductEntity>>

   fun getProductEntityByName(productName: String): Flow<ProductEntity?>
}

