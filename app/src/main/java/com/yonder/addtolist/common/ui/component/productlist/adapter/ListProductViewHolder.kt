package com.yonder.addtolist.common.ui.component.productlist.adapter

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.component.productlist.IProductOperation
import com.yonder.addtolist.common.ui.extensions.compatColor
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.common.ui.extensions.setStrikeThrough
import com.yonder.addtolist.core.extensions.toBoolean
import com.yonder.addtolist.databinding.ItemListProductBinding
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

class ListProductViewHolder(
  view: View
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemListProductBinding.bind(view)

  fun bind(product: UserListProductEntity, productOperation: IProductOperation) = with(binding) {
    val isDone = product.wrappedDone()
    val isFavorite = product.favorite.toBoolean()
    bindTitle(product.name, isDone)
    bindNote(product.note)
    bindCategory(product.wrappedCategoryImage(), isDone)
    bindEditBtn(product, productOperation)
    bindFavorite(isFavorite)
    itemView.setSafeOnClickListener {
      productOperation.toggleDone(product)
    }
  }

  private fun bindFavorite(isFavorite: Boolean) = with(binding.ivFavorite) {
    isVisible = isFavorite
  }

  private fun bindTitle(productName: String?, isDone: Boolean) = with(binding.titleTextView) {
    setStrikeThrough(isDone)
    text = productName
    if (isDone) {
      setTextColor(context.compatColor(R.color.colorGray))
    } else {
      setTextColor(context.compatColor(R.color.primaryTextColor))
    }
  }

  private fun bindEditBtn(product: UserListProductEntity, productOperation: IProductOperation) =
    with(binding.imgBtnEdit) {
      setSafeOnClickListener {
        productOperation.edit(product)
      }
      isGone = product.wrappedDone()
    }

  private fun bindNote(note: String?) = with(binding.tvNote) {
    isGone = note.isNullOrEmpty()
    text = note
  }

  private fun bindCategory(categoryImage: String, isDone: Boolean) = with(binding.tvCategory) {
    isGone = isDone
    text = categoryImage
  }

}
