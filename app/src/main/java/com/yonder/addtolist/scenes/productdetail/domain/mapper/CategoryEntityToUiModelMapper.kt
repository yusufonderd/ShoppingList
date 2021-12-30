package com.yonder.addtolist.scenes.productdetail.domain.mapper

import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.scenes.home.domain.model.CategoryUiModel
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

class CategoryEntityToUiModelMapper @Inject constructor() :
  Mapper<CategoryEntity, CategoryUiModel> {
  override fun map(input: CategoryEntity): CategoryUiModel {
    return CategoryUiModel(name = input.name, image = input.image)
  }
}
