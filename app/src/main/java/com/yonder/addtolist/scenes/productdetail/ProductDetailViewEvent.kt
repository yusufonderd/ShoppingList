package com.yonder.addtolist.scenes.productdetail

import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.uimodel.CategoryUiModel

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */

sealed class ProductDetailViewEvent {
  object ProductNotFound : ProductDetailViewEvent()
  data class Load(
      var categories: List<CategoryUiModel>,
      var product: UserListProductUiModel,
      var categoryOfProduct: CategoryUiModel?
    ) : ProductDetailViewEvent()
}
