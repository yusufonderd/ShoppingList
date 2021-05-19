package com.yonder.addtolist.features.list.data.local.mapper

import com.yonder.addtolist.core.base.BaseMapper
import com.yonder.addtolist.core.base.BaseUiResult
import com.yonder.addtolist.features.list.data.local.entity.CategoryEntity
import com.yonder.addtolist.features.list.domain.model.CategoryProductsUiModel

/**
 * Yusuf Onder on 12,May,2021
 */

class CategoryEntityMapper : BaseMapper<CategoryEntity, CategoryProductsUiModel> {

  override fun map(input: CategoryEntity): CategoryProductsUiModel {

    return CategoryProductsUiModel(
      BaseUiResult(true, ""),
      emptyList()
    )
  }

}