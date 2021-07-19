package com.yonder.addtolist.common.utils.decider

import android.content.Context
import androidx.annotation.ColorRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
class ColorDecider @Inject constructor(@ApplicationContext context: Context) {

  fun convertToHexString(@ColorRes colorResId: Int): String {
    return String.format("#%06X", 0xFFFFFF and colorResId)
  }
}