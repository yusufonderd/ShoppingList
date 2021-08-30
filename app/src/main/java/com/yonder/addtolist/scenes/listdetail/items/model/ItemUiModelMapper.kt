package com.yonder.addtolist.scenes.listdetail.items.model

import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.domain.model.ProductEntityUiModel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
object ItemUiModelMapper {

  fun mapToUiModel(
    addedProducts: List<UserListProductUiModel>,
    filteredProducts: List<ProductEntityUiModel>
  ): List<ItemUiModel> {
    return filteredProducts.map { summary ->
      ItemUiModel(
        name = summary.name,
        product = addedProducts.find { summary.name == it.name }
      )
    }
  }
}
