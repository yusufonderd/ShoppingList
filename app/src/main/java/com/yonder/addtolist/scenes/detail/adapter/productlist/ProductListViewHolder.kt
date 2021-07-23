package com.yonder.addtolist.scenes.detail.adapter.productlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.databinding.ItemProductBinding
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */

class ProductListViewHolder(
  view: View,
  private val listener: IProductOperation
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemProductBinding.bind(view)

  fun bind(value: ProductEntitySummary, product: UserListProductEntity? = null) = with(binding) {
    binding.yoProductView.bind(value, listener, product)

  }
}