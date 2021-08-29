package com.yonder.addtolist.scenes.home.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Yusuf Onder on 12,May,2021
 */

data class CategoryProductResponse(
  @SerializedName("id") var id: Int?,
  @SerializedName("name") var name: String?,
  @SerializedName("image") var image: String?,
  @SerializedName("translations") var translationResponses: ArrayList<TranslationResponse>?,
  @SerializedName("products") var products: ArrayList<ProductResponse>?
) {
  fun wrappedName(): String {
    return translationResponses?.firstOrNull()?.name.orEmpty()
  }
}
