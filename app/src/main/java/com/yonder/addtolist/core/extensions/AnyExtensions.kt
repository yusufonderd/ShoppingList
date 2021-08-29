package com.yonder.addtolist.core.extensions

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */

fun Any?.toInt(): Int {
  return if (this is Boolean) {
    if (this == true) {
      1
    } else {
      0
    }
  } else if (this is Int) {
    this
  } else {
    0
  }
}
