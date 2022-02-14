package com.yonder.addtolist.uicomponent

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

fun RecyclerView.addVerticalDivider() {
  (layoutManager as? LinearLayoutManager)?.let {
    val dividerItemDecoration = DividerItemDecoration(
      context,
      it.orientation
    )
    addItemDecoration(dividerItemDecoration)
  }
}