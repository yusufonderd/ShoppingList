package com.yonder.addtolist.core.network.responses

import com.yonder.addtolist.core.extensions.orFalse

/**
 * Yusuf Onder on 09,May,2021
 */

class BaseResponse<T> {
  val success: Boolean? = null
  val message: String? = null
  val data: T? = null
  fun toBaseUiResult(): BaseUiResult {
    return BaseUiResult(success.orFalse(), message.orEmpty())
  }
}


data class BaseUiResult(var success: Boolean, val message: String)
