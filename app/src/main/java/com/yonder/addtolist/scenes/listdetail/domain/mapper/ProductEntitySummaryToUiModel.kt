package com.yonder.addtolist.scenes.listdetail.domain.mapper

import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.scenes.listdetail.domain.model.ProductEntityUiModel
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */
class ProductEntitySummaryToUiModel @Inject constructor() :
  Mapper<ProductEntitySummary, ProductEntityUiModel> {
  override fun map(input: ProductEntitySummary): ProductEntityUiModel {
    return ProductEntityUiModel(name = input.name, categoryImage = input.categoryImage.orEmpty())
  }
}
