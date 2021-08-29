package com.yonder.uicomponent.items.model

import com.yonder.uicomponent.base.model.UserListProductSummaryUiModel
import com.yonder.uicomponent.base.model.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
object ItemUiModelMapper {
  fun mapToUiModel(
    addedProducts: List<UserListProductUiModel>,
    filteredProducts: List<UserListProductSummaryUiModel>
  ): List<ItemUiModel> {
    return filteredProducts.map { summary ->
      ItemUiModel(
        name = summary.name,
        addedProducts.find { summary.name == it.name }
      )
    }
  }
}
