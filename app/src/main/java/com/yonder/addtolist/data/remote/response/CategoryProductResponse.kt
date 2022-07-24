package com.yonder.addtolist.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Yusuf Onder on 12,May,2021
 */

@Keep
data class CategoryProductResponse(
    @SerializedName("id") var id: Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("image") var image: String?,
    @SerializedName("translations") var translationResponses: ArrayList<TranslationResponse>?,
    @SerializedName("products") var products: ArrayList<ProductResponse>?
)
