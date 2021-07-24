package com.yonder.addtolist.common.ui.component.items.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.common.ui.component.items.ItemOperationListener
import com.yonder.addtolist.common.ui.component.items.model.ItemUiModel
import com.yonder.addtolist.databinding.ItemProductBinding

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */

class ItemListViewHolder(
  view: View,
  private val listener: ItemOperationListener
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemProductBinding.bind(view)

  fun bind(value: ItemUiModel, query: String) {
    binding.yoProductView.bind(value, listener, query)
  }
}