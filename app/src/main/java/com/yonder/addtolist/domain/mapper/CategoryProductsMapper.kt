package com.yonder.addtolist.domain.mapper

import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.data.remote.response.CategoryProductResponse
import com.yonder.addtolist.domain.decider.CategoryProductsDecider
import com.yonder.addtolist.domain.uimodel.CategoryProductsUiModel
import com.yonder.addtolist.domain.uimodel.CategoryWithProductsUiModel
import com.yonder.addtolist.domain.uimodel.ProductUiModel
import com.yonder.addtolist.domain.uimodel.TranslationUiModel
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class CategoryProductsMapper @Inject constructor(private val decider: CategoryProductsDecider) :
  Mapper<BaseResponse<ArrayList<CategoryProductResponse>>, CategoryProductsUiModel> {

  override fun map(input: BaseResponse<ArrayList<CategoryProductResponse>>): CategoryProductsUiModel {
    val list = input.data?.filter { response ->
      response.id != null && response.name != null && response.products.isNullOrEmpty()
        .not() && response.translationResponses.isNullOrEmpty().not()
    }?.map { response ->
      val translations = response.translationResponses?.map { translation ->
        TranslationUiModel(
          translation.name.orEmpty(),
          translation.languageId.orZero(),
          translation.categoryId.orZero()
        )
      }.orEmpty()
      val products = response.products?.map { product ->
        ProductUiModel(
          product.id.orZero(),
          product.name.orEmpty(),
          decider.isPopular(product.popular),
          product.languageId.orZero()
        )
      }.orEmpty()
      CategoryWithProductsUiModel(
        response.id.orZero(),
        response.name.orEmpty(),
        response.image.orEmpty(),
        translations,
        products
      )
    }.orEmpty()
    return CategoryProductsUiModel(
      result = decider.toBaseUiResult(input),
      list = list
    )
  }

}
