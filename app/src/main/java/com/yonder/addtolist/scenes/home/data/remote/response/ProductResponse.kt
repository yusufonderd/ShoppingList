package com.yonder.addtolist.scenes.home.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Yusuf Onder on 12,May,2021
 */

data class ProductResponse(
  @SerializedName("id") var id: Int?,
  @SerializedName("name") var name: String?,
  @SerializedName("popular") var popular: Int?,
  @SerializedName("language_id") var languageId: Int?
)