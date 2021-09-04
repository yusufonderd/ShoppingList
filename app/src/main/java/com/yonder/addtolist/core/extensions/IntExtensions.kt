package com.yonder.addtolist.core.extensions

/**
 * Yusuf Onder on 12,May,2021
 */

const val LENGTH_ZERO = 0
const val INDEX_NOT_FOUND = -1
const val FIRST_INDEX = 0



fun Int?.orZero() = this ?: 0

fun Int?.toBoolean(): Boolean{
  return when(this){
    1 -> true
    0 -> false
    else -> false
  }
}
