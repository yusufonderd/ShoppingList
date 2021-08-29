package com.yonder.addtolist.common.ui.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

fun EditText.cursorEnd() {
  setSelection(text.length);
}

fun EditText.openWithKeyboard(context: Context) {
  requestFocus()
  val imm: InputMethodManager? =
    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
  imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
