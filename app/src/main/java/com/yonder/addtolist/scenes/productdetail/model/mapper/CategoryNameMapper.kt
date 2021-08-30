package com.yonder.addtolist.scenes.productdetail.model.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.scenes.productdetail.model.CategoryUiModel

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */
class CategoryNameMapper : Mapper<List<CategoryEntity>, List<CategoryUiModel>> {

  override fun map(input: List<CategoryEntity>): List<CategoryUiModel> {
    val sortedCategories = ArrayList<CategoryEntity>(input.sortedBy { it.name })
    return sortedCategories.map {
      CategoryUiModel(
        name = it.name,
        image = it.image
      )
    }
  }

}
