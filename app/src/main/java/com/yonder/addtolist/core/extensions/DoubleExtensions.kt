package com.yonder.addtolist.core.extensions

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */

const val ZERO: Double = 0.0

fun Double?.orZero() = this ?: 0.0
