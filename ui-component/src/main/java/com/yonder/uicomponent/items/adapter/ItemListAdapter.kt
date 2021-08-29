package com.yonder.uicomponent.items.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.uicomponent.items.ItemOperationListener
import com.yonder.uicomponent.items.model.ItemUiModel
import com.yonder.uicomponent.R
import com.yonder.uicomponent.base.BaseListAdapter
import com.yonder.uicomponent.constants.EMPTY_STRING

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

class ItemListAdapter :
  BaseListAdapter<ItemUiModel>(
    itemsSame = { old, new -> old.name == new.name || old.product?.quantity == new.product?.quantity },
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
