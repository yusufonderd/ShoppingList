package com.yonder.addtolist.scenes.listdetail.productlist

import com.yonder.addtolist.domain.uimodel.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
interface ListProductCallbacks {
  fun edit(item: UserListProductUiModel)
  fun toggleDone(item: UserListProductUiModel, productPosition : Int)
}
