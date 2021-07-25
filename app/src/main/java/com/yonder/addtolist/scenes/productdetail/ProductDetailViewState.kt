package com.yonder.addtolist.scenes.productdetail

import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */

sealed class ProductDetailViewState {
  data class Load(
    var categories: List<CategoryEntity>,
    var userListProduct: UserListProductEntity?) : ProductDetailViewState()
}