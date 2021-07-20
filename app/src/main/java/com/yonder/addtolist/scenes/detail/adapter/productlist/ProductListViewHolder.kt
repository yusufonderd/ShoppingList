package com.yonder.addtolist.scenes.detail.adapter.productlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.databinding.ItemProductBinding
import com.yonder.addtolist.local.entity.ProductEntitySummary

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */

class ProductListViewHolder(
  view: View,
  private val onClickProduct: ((value: ProductEntitySummary) -> Unit)
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemProductBinding.bind(view)

  fun bind(value: ProductEntitySummary) = with(binding) {
    binding.tvProductTitle.text = value.name
    binding.root.setSafeOnClickListener {
      onClickProduct.invoke(value)
    }
  }
}