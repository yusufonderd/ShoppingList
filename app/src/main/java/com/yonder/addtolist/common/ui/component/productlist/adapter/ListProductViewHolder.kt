package com.yonder.addtolist.common.ui.component.productlist.adapter

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.common.ui.component.productlist.IProductOperation
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
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

  fun bind(value: UserListProductEntity,productOperation : IProductOperation) {
    binding.tvNote.isGone = value.note.isNullOrEmpty()
    binding.tvNote.text = value.note
    binding.tvCategory.text = value.wrappedCategoryImage()
    binding.ivFavorite.isVisible = value.favorite.toBoolean()
    binding.titleTextView.text = value.name.orEmpty()
    binding.imgBtnEdit.setSafeOnClickListener {
      productOperation.edit(value)
    }
  }
}