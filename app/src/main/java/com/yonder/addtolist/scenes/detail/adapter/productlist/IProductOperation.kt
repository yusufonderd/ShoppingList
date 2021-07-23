package com.yonder.addtolist.scenes.detail.adapter.productlist

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
  fun addProduct(product: ProductEntitySummary)
}