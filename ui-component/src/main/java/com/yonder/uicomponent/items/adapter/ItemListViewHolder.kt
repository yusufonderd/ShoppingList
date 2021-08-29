package com.yonder.uicomponent.items.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.uicomponent.databinding.ItemProductBinding
import com.yonder.uicomponent.items.ItemOperationListener
import com.yonder.uicomponent.items.model.ItemUiModel

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
