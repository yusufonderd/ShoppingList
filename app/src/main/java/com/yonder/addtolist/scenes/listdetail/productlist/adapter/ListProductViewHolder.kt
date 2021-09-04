package com.yonder.addtolist.scenes.listdetail.productlist.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.setStrikeThrough
import com.yonder.addtolist.databinding.ItemListProductBinding
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.productlist.UserListProductOperationListener

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

class ListProductViewHolder(
  view: View
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemListProductBinding.bind(view)

  fun bind(
    product: UserListProductUiModel,
    position : Int,
    productOperationListener: UserListProductOperationListener
  ) = with(binding) {
    val isDone = product.isDone
    val isFavorite = product.isFavorite
    bindTitle(product.name, isDone)
    bindNote(product.note)
    bindCategory(product.categoryUnicode, isDone)
    bindEditBtn(product, productOperationListener)
    bindFavorite(isFavorite)
    itemView.setOnClickListener {
      productOperationListener.toggleDone(product,position)
    }
  }

  private fun bindFavorite(isFavorite: Boolean) = with(binding.ivFavorite) {
    isVisible = isFavorite
  }

  private fun bindTitle(productName: String?, isDone: Boolean) = with(binding.titleTextView) {
    setStrikeThrough(isDone)
    text = productName
    if (isDone) {
      setTextColor(
        ContextCompat.getColor(context, R.color.colorGray)
      )
    } else {
      setTextColor(
        ContextCompat.getColor(context, R.color.primaryTextColor)
      )
    }
  }

  private fun bindEditBtn(
    product: UserListProductUiModel,
    productOperationListener: UserListProductOperationListener
  ) =
    with(binding.imgBtnEdit) {
      setOnClickListener {
        productOperationListener.edit(product)
      }
      isGone = product.isDone
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
