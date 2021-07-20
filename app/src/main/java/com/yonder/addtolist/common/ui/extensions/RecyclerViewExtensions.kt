package com.yonder.addtolist.common.ui.extensions

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */


fun RecyclerView.removeAnimator() {
  itemAnimator = null
}

fun RecyclerView.addVerticalDivider() {
  (layoutManager as? LinearLayoutManager)?.let {
    val dividerItemDecoration = DividerItemDecoration(
      context,
      it.orientation
    )
    addItemDecoration(dividerItemDecoration)
  }

}
