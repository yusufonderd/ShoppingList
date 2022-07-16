package com.yonder.addtolist.core.extensions

/**
 * @author yusuf.onder
 * Created on 23.07.2021
 */

const val EMPTY_STRING = ""
const val SPACE_STRING = " "

fun String.toSafeDouble(): Double = this.replace(",", ".").toDoubleOrNull().orZero()