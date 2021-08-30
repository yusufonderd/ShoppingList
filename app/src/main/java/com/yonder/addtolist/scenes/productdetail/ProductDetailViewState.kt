package com.yonder.addtolist.scenes.productdetail

import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.home.domain.model.CategoryUiModel

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */

sealed class ProductDetailViewState {
  data class Load(
    var categories: List<CategoryUiModel>,
    var product: UserListProductUiModel,
    var categoryOfProduct: CategoryUiModel?
    ) : ProductDetailViewState()
}
