package com.yonder.uicomponent.items

import com.yonder.uicomponent.base.model.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 23.07.2021
 */
interface ItemOperationListener {
  fun decreaseProductQuantity(productEntity: UserListProductUiModel)
  fun increaseProductQuantity(productEntity: UserListProductUiModel)
  fun removeProductEntity(productEntity: UserListProductUiModel)
  fun addProduct(productName: String)
}
