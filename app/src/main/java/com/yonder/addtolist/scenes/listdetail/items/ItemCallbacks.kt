package com.yonder.addtolist.scenes.listdetail.items

import com.yonder.addtolist.domain.uimodel.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 23.07.2021
 */
interface ItemCallbacks {
  fun decreaseQuantity(item: UserListProductUiModel)
  fun increaseQuantity(item: UserListProductUiModel)
  fun removeProduct(item: UserListProductUiModel)
  fun addProduct(productName: String)
}
