package com.yonder.addtolist.core.network.responses

import com.google.gson.annotations.SerializedName
import com.yonder.addtolist.core.extensions.orFalse

/**
 * Yusuf Onder on 09,May,2021
 */

class BaseResponse<T> {
    @SerializedName("success")
    val success: Boolean? = null
    @SerializedName("message")
    val message: String? = null
    @SerializedName("data")
    val data: T? = null
    fun toBaseUiResult(): BaseUiResult {
        return BaseUiResult(success.orFalse(), message.orEmpty())
    }
}


data class BaseUiResult(var success: Boolean, val message: String)
