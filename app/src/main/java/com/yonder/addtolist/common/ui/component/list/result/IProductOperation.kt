package com.yonder.addtolist.common.ui.component.list.result

import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 23.07.2021
 */
interface IProductOperation {
  fun decreaseProductQuantity(productEntity: UserListProductEntity)
  fun increaseProductQuantity(productEntity: UserListProductEntity)
  fun removeProductEntity(productEntity: UserListProductEntity)
  fun addProduct(productName: String)
}