package com.yonder.addtolist.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */
@Keep
data class LanguageResponse(
    @SerializedName("name") var name: String?,
    @SerializedName("image") var image: String?,
    @SerializedName("tag") var tag: String?,
    @SerializedName("id") var id: Int
)
