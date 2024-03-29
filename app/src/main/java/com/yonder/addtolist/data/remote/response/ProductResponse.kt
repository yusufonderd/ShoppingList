package com.yonder.addtolist.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Yusuf Onder on 12,May,2021
 */
@Keep
data class ProductResponse(
  @SerializedName("id") var id: Int?,
  @SerializedName("name") var name: String?,
  @SerializedName("popular") var popular: Int?,
  @SerializedName("language_id") var languageId: Int?
)
