package com.yonder.addtolist.features.list.data.local.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.core.network.responses.BaseUiResult
import com.yonder.addtolist.features.list.data.local.entity.CategoryEntity
import com.yonder.addtolist.features.list.domain.model.CategoryProductsUiModel

/**
 * Yusuf Onder on 12,May,2021
 */

class CategoryEntityMapper : Mapper<CategoryEntity, CategoryProductsUiModel> {

  override fun map(input: CategoryEntity): CategoryProductsUiModel {

    return CategoryProductsUiModel(
      BaseUiResult(true, ""),
      emptyList()
    )
  }

}