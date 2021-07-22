package com.yonder.addtolist.scenes.detail.domain.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.scenes.list.domain.model.TranslationUiModel

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
class CategoryEntityMapper(
  private val categoryImage: String
) : Mapper<TranslationUiModel, CategoryEntity> {
  override fun map(input: TranslationUiModel): CategoryEntity {
    return CategoryEntity("${input.categoryId}", input.name, categoryImage, input.languageId)
  }
}