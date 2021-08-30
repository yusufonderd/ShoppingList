package com.yonder.addtolist.scenes.home.presentation.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.compatColor
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.databinding.ItemUserListBinding
import com.yonder.addtolist.scenes.home.domain.model.UserListUiModel

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

class UserListViewHolder(
  view: View,
  private val onClickUserList: ((value: UserListUiModel) -> Unit)
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemUserListBinding.bind(view)

  fun bind(userList: UserListUiModel) = with(binding) {
    tvListName.text = userList.name
    bindProductCountText(userList)
    bindUncompletedItemsText(userList)
    bindClickListeners(userList)
  }

  @Suppress("TooGenericExceptionCaught")
  private fun bindProductCountText(userList: UserListUiModel){
    binding.cvListProductsCount.isGone = userList.products.isEmpty()
    binding.tvListProductCount.text = "${userList.products.size}"
    val backgroundTintList = try {
      ColorStateList.valueOf(Color.parseColor(userList.color))
    } catch (e: Exception) {
      ColorStateList.valueOf(itemView.context.compatColor(R.color.colorBlue))
    }
    binding.cvListProductsCount.setCardBackgroundColor(backgroundTintList)
  }

  private fun bindUncompletedItemsText(userList: UserListUiModel){
    val isVisible = userList.uncompletedItems.isNotBlank()
    binding.tvListUncompletedItems.isVisible = isVisible
    binding.tvListUncompletedItems.text = userList.uncompletedItems
  }
  private fun bindClickListeners(value: UserListUiModel) {
    binding.root.setSafeOnClickListener {
      onClickUserList.invoke(value)
    }
  }
}
