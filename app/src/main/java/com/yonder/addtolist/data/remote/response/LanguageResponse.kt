package com.yonder.addtolist.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */
data class LanguageResponse(
    @SerializedName("name") var name: String?,
    @SerializedName("image") var image: String?,
    @SerializedName("tag") var tag: String?,
    @SerializedName("id") var id: Int
)
