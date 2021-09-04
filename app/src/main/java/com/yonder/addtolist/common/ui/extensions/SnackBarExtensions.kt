package com.yonder.addtolist.common.ui.extensions

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */

fun ViewBinding.showSnackBar(
  message: String,
  duration: Int = Snackbar.LENGTH_LONG
) = (this.root as ViewGroup).showSnackBar(message, duration)

fun ViewGroup.showSnackBar(
  message: String,
  duration: Int = Snackbar.LENGTH_LONG
) = Snackbar.make(this, message, duration).show()

