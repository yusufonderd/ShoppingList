package com.yonder.addtolist.domain.uimodel

import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.local.entity.CategoryEntity

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
class CategoryEntityMapper(
  private val categoryImage: String
) : Mapper<TranslationUiModel, CategoryEntity> {
  override fun map(input: TranslationUiModel): CategoryEntity {
    return CategoryEntity(
      categoryId = "${input.categoryId}$SEPARATOR${input.languageId}",
      name = input.name,
      image = categoryImage,
      languageId = input.languageId
    )
  }

  companion object {
    private const val SEPARATOR = "-"
  }
}


