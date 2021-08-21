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
import com.yonder.addtolist.core.extensions.LENGTH_ZERO
import com.yonder.addtolist.databinding.ItemUserListBinding
import com.yonder.addtolist.local.entity.UserListWithProducts

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

class UserListViewHolder(
  view: View,
  private val onClickUserList: ((value: UserListWithProducts) -> Unit)
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemUserListBinding.bind(view)

  fun bind(userList: UserListWithProducts) = with(binding) {
    tvListName.text = userList.userList.name
    bindProductCountText(userList)
    bindUncompletedItemsText(userList)
    bindClickListeners(userList)
  }

  private fun bindProductCountText(userList: UserListWithProducts){
    binding.cvListProductsCount.isGone = userList.products.isEmpty()
    binding.tvListProductCount.text = "${userList.products.size}"
    val backgroundTintList = try {
      ColorStateList.valueOf(Color.parseColor(userList.userList.color))
    } catch (e: Exception) {
      ColorStateList.valueOf(itemView.context.compatColor(R.color.colorBlue))
    }
    binding.cvListProductsCount.setCardBackgroundColor(backgroundTintList)
  }

  private fun bindUncompletedItemsText(userList: UserListWithProducts){
    val isVisible = userList.wrappedUncompletedItemsCount() != LENGTH_ZERO
    binding.tvListUncompletedItems.isVisible = isVisible
    binding.tvListUncompletedItems.text = userList.wrappedUncompletedItems()
  }
  private fun bindClickListeners(value: UserListWithProducts) {
    binding.root.setSafeOnClickListener {
      onClickUserList.invoke(value)
    }
  }


}