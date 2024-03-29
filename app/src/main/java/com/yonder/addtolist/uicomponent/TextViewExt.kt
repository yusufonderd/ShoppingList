package com.yonder.addtolist.uicomponent

import android.graphics.Paint
import android.widget.TextView

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */
private const val NO_PAINT_FLAG = 0

fun TextView.setStrikeThrough( active: Boolean) {
  paintFlags = if (active) {
    paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
  } else {
    NO_PAINT_FLAG
  }
}