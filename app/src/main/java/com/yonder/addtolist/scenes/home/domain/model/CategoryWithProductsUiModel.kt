package com.yonder.addtolist.scenes.home.domain.model

/**
 * Yusuf Onder on 12,May,2021
 */

data class CategoryWithProductsUiModel(
  var id: Int,
  var name: String,
  var image: String,
  var translationResponses: List<TranslationUiModel>,
  var products: List<ProductUiModel>
)