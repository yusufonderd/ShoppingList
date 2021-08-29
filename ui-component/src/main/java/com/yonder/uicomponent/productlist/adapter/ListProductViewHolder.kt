package com.yonder.uicomponent.productlist.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yonder.uicomponent.productlist.IProductOperation
import com.yonder.uicomponent.R
import com.yonder.uicomponent.databinding.ItemListProductBinding
import com.yonder.uicomponent.extensions.setStrikeThrough
import com.yonder.uicomponent.base.model.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

class ListProductViewHolder(
  view: View
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemListProductBinding.bind(view)

  fun bind(product: UserListProductUiModel, productOperation: IProductOperation) = with(binding) {
   /* val isDone = product.wrappedDone()
    val isFavorite = product.favorite.toBoolean()
    bindTitle(product.name, isDone)
    bindNote(product.note)
    bindCategory(product.wrappedCategoryImage(), isDone)
    bindEditBtn(product, productOperation)
    bindFavorite(isFavorite)
    itemView.setOnClickListener {
      productOperation.toggleDone(product)
    }*/
    val isDone = product.isDone
    val isFavorite = product.isFavorite
    bindTitle(product.name, isDone)
    bindNote(product.note)
    bindCategory(product.categoryImage, isDone)
    bindEditBtn(product, productOperation)
    bindFavorite(isFavorite)
    itemView.setOnClickListener {
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
      setTextColor(
        ContextCompat.getColor(context, R.color.colorGray)
      )
    } else {
      setTextColor(
        ContextCompat.getColor(context, R.color.primaryTextColor)
      )
    }
  }

  private fun bindEditBtn(product: UserListProductUiModel, productOperation: IProductOperation) =
    with(binding.imgBtnEdit) {
      setOnClickListener {
        productOperation.edit(product)
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
