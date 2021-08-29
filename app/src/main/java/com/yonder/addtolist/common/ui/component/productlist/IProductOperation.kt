package com.yonder.addtolist.common.ui.component.productlist

import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
interface IProductOperation {
  fun edit(product: UserListProductEntity)
  fun toggleDone(product: UserListProductEntity)
}
