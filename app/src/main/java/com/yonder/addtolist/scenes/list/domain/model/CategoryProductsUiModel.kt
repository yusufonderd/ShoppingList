package com.yonder.addtolist.scenes.list.domain.model

import com.yonder.addtolist.core.network.responses.BaseUiModel
import com.yonder.addtolist.core.network.responses.BaseUiResult

/**
 * Yusuf Onder on 12,May,2021
 */

data class CategoryProductsUiModel(
  override val result: BaseUiResult,
  var list: List<CategoryWithProductsUiModel>
) : BaseUiModel()



