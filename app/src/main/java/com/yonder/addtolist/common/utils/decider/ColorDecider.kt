package com.yonder.addtolist.common.utils.decider

import androidx.annotation.ColorRes
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
@Suppress("MagicNumber")
class ColorDecider @Inject constructor() {

  fun convertToHexString(@ColorRes colorResId: Int): String {
    return String.format("#%06X", 0xFFFFFF and colorResId)
  }
}
