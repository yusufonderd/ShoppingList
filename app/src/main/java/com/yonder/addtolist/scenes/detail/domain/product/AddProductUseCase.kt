package com.yonder.addtolist.scenes.detail.domain.product

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity
import kotlinx.coroutines.flow.Flow
/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
interface AddProductUseCase {
  fun addProduct(listUUID: String, product: ProductEntitySummary): Flow<Result<UserListProductEntity>>
}
