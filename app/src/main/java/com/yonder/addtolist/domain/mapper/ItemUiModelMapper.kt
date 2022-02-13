package com.yonder.addtolist.domain.mapper

import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.uimodel.ProductEntityUiModel
import com.yonder.addtolist.scenes.listdetail.items.model.ItemUiModel

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
