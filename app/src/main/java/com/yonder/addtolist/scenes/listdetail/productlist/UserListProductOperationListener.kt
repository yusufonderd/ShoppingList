package com.yonder.addtolist.scenes.listdetail.productlist

import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
interface UserListProductOperationListener {
  fun edit(item: UserListProductUiModel)
  fun toggleDone(item: UserListProductUiModel,productPosition : Int)
}
