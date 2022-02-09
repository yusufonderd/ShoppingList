package com.yonder.addtolist.common.ui.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */


fun Context.compatColor(@ColorRes colorResId: Int) = ContextCompat.getColor(this, colorResId)

fun Context.compatDrawable(@DrawableRes drawableResId: Int) =
  ContextCompat.getDrawable(this, drawableResId)

fun Context.showToastMessage(@StringRes messageResId: Int) {
  Toast.makeText(this, getString(messageResId), Toast.LENGTH_LONG).show()
}

fun Context.showToastMessage(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


