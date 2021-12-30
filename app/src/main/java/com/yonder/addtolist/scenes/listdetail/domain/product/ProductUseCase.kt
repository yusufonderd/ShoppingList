package com.yonder.addtolist.scenes.listdetail.domain.product

import com.yonder.core.network.RestResult
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
interface ProductUseCase {
  fun getProductEntityForName(name: String) : Flow<ProductEntity?>
//  fun removeProduct(productEntity: UserListProductEntity): Flow<Result<UserListProductEntity>>
  fun updateProduct(listId: String,productEntity: UserListProductEntity): Flow<RestResult<UserListProductEntity>>
}
