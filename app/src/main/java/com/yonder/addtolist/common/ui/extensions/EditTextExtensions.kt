package com.yonder.addtolist.common.ui.extensions

import android.widget.EditText

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

fun EditText.cursorEnd(){
  setSelection(text.length);
}
