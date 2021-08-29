package com.yonder.uicomponent.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.uicomponent.R
import com.yonder.uicomponent.productlist.IProductOperation
import com.yonder.uicomponent.base.BaseListAdapter
import com.yonder.uicomponent.base.model.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

class ListProductAdapter :
  BaseListAdapter<UserListProductUiModel>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
  ) {

  lateinit var iProductOperation: IProductOperation

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

        holder.bind(product, iProductOperation)
      }
    }
  }

}
