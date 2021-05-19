package com.yonder.addtolist.features.list.domain.model

import com.yonder.addtolist.core.base.BaseUiModel
import com.yonder.addtolist.core.base.BaseUiResult

/**
 * Yusuf Onder on 12,May,2021
 */

data class CategoryProductsUiModel(
  override val result: BaseUiResult,
  var list: List<CategoryWithProductsUiModel>
) : BaseUiModel()



