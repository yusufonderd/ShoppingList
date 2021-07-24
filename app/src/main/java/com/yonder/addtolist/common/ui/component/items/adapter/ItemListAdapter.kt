package com.yonder.addtolist.common.ui.component.items.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseListAdapter
import com.yonder.addtolist.common.ui.component.items.ItemOperationListener
import com.yonder.addtolist.common.ui.component.items.model.ItemUiModel
import com.yonder.addtolist.core.extensions.EMPTY_STRING

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

class ItemListAdapter :
  BaseListAdapter<ItemUiModel>(
    itemsSame = { old, new -> old.name == new.name || old.entity?.quantity == new.entity?.quantity },
    contentsSame = { old, new -> old == new }
  ) {

  lateinit var itemOperationListener: ItemOperationListener

  var query : String = EMPTY_STRING

  override fun onCreateViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    viewType: Int
  ): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    return ItemListViewHolder(view, itemOperationListener)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is ItemListViewHolder -> {
        val product = getItem(position)
        holder.bind(product,query)
      }
    }
  }

}