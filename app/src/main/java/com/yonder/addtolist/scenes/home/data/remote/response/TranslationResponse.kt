package com.yonder.addtolist.scenes.home.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Yusuf Onder on 12,May,2021
 */

data class TranslationResponse(
  @SerializedName("name") var name: String?,
  @SerializedName("language_id") var languageId: Int?,
  @SerializedName("category_id") var categoryId: Int?
)