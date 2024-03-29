package com.yonder.addtolist.domain.uimodel

import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.domain.uimodel.ProductUiModel

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
class ProductEntityMapper(
  private val categoryId: String,
  private val categoryImage: String
) : Mapper<ProductUiModel, ProductEntity> {
  override fun map(input: ProductUiModel): ProductEntity {
    return ProductEntity(
      input.id,
      categoryId,
      input.name,
      input.isPopular,
      input.languageId,
      categoryImage
    )
  }
}
