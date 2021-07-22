package com.yonder.addtolist.scenes.detail.adapter.productlist

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.databinding.ItemProductBinding
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */

class ProductListViewHolder(
  view: View,
  private val onClickProduct: ((value: ProductEntitySummary) -> Unit)
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemProductBinding.bind(view)

  fun bind(value: ProductEntitySummary, product: UserListProductEntity? = null) = with(binding) {
    binding.tvProductTitle.text = value.name
    if (product != null) {
      binding.tvProductTitle.setTextColor(Color.RED)
    } else {
      binding.tvProductTitle.setTextColor(
        ContextCompat.getColor(
          binding.root.context,
          R.color.primaryTextColor
        )
      )

    }
    binding.root.setSafeOnClickListener {
      onClickProduct.invoke(value)
    }
  }
}