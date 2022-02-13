package com.yonder.addtolist.domain.uimodel

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
