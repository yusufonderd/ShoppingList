package com.yonder.addtolist.core.extensions

/**
 * Yusuf Onder on 12,May,2021
 */

fun Int?.orZero() = this ?: 0

fun Int?.toBoolean(): Boolean{
  return when(this){
    1 -> true
    0 -> false
    else -> false
  }
}
