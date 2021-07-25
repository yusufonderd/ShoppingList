package com.yonder.addtolist.common.ui.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */


fun Context.compatColor(@ColorRes colorResId: Int) = ContextCompat.getColor(this, colorResId)

fun Context.compatDrawable(@DrawableRes drawableResId: Int) = ContextCompat.getDrawable(this,drawableResId)