package com.yonder.addtolist.scenes.listdetail.items

import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 23.07.2021
 */
interface ItemOperationListener {
  fun decreaseQuantity(item: UserListProductUiModel)
  fun increaseQuantity(item: UserListProductUiModel)
  fun removeProduct(item: UserListProductUiModel)
  fun addProduct(productName: String)
}

