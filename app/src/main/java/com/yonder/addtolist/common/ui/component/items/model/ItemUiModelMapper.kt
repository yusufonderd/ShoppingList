package com.yonder.addtolist.common.ui.component.items.model

import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
object ItemUiModelMapper {
  fun mapToUiModel(
    addedProducts: List<UserListProductEntity>,
    filteredProducts: List<ProductEntitySummary>
  ): List<ItemUiModel> {
    return filteredProducts.map { summary ->
      ItemUiModel(
        name = summary.name,
        addedProducts.find { summary.name == it.name }
      )
    }
  }
}
