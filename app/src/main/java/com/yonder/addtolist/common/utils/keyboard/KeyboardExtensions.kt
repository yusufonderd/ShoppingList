package com.yonder.addtolist.common.utils.keyboard

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

fun Context?.hideKeyboardFor(editText: EditText) {
  val imm = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(editText.applicationWindowToken, 0)
}
