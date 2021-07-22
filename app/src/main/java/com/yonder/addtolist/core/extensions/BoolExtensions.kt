package com.yonder.addtolist.core.extensions

/**
 * Yusuf Onder on 11,May,2021
 */

fun Boolean?.orFalse() = this ?: false

fun Boolean?.toInt(): Int {
  return when (this) {
    true -> 1
    false -> 0
    else -> 0
  }
}