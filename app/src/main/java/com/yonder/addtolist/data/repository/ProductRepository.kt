package com.yonder.addtolist.data.repository

import com.yonder.addtolist.core.network.request.CreateUserListProductRequest
import com.yonder.core.network.RestResult
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
interface ProductRepository {
  fun addProduct(
    listUUID: String,
    product: CreateUserListProductRequest
  ): Flow<RestResult<UserListProductEntity>>

  suspend fun delete(product: UserListProductUiModel)
  fun updateProduct(
    listId: String,
    product: UserListProductEntity
  ): Flow<RestResult<UserListProductEntity>>

   fun getProductEntityByName(productName: String): Flow<ProductEntity?>
}

