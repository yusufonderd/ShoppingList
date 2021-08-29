package com.yonder.uicomponent.productlist

import com.yonder.uicomponent.base.model.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
interface IProductOperation {
  fun edit(product: UserListProductUiModel)
  fun toggleDone(product: UserListProductUiModel)
}
