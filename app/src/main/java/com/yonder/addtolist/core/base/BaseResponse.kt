package com.yonder.addtolist.core

/**
 * Yusuf Onder on 09,May,2021
 */

class BaseResponse<T> {
  val success: Boolean? = null
  val message: String? = null
  val data: T? = null

  fun toBaseUiResult(): BaseUiResult {
    return BaseUiResult(success ?: false, message.orEmpty())
  }
}


data class BaseUiResult(var success: Boolean, val message: String)