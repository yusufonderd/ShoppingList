package com.yonder.addtolist.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Yusuf Onder on 12,May,2021
 */

@Keep
data class TranslationResponse(
  @SerializedName("name") var name: String?,
  @SerializedName("language_id") var languageId: Int?,
  @SerializedName("category_id") var categoryId: Int?
)
