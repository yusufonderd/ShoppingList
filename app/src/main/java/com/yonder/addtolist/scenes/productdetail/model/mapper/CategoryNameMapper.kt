package com.yonder.addtolist.scenes.productdetail.model.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.scenes.productdetail.model.CategoryUIModel

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */
class CategoryNameMapper : Mapper<List<CategoryEntity>, List<CategoryUIModel>> {

  override fun map(input: List<CategoryEntity>): List<CategoryUIModel> {
    val sortedCategories = ArrayList<CategoryEntity>(input.sortedBy { it.name })
    return sortedCategories.map {
      CategoryUIModel(
        name = it.name,
        image = it.image
      )
    }
  }

}
