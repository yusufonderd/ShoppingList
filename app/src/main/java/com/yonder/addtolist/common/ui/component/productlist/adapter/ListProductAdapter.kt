package com.yonder.addtolist.common.ui.component.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseListAdapter
import com.yonder.addtolist.common.ui.component.items.ItemOperationListener
import com.yonder.addtolist.common.ui.component.items.adapter.ItemListViewHolder
import com.yonder.addtolist.common.ui.component.items.model.ItemUiModel
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

class ListProductAdapter :
  BaseListAdapter<UserListProductEntity>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
  ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    viewType: Int
  ): RecyclerView.ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.item_list_product, parent, false)
    return ListProductViewHolder(view)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is ListProductViewHolder -> {
        val product = getItem(position)
        holder.bind(product)
      }
    }
  }

}