package com.yonder.addtolist.scenes.home.presentation.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.compatColor
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
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

  fun bind(value: UserListWithProducts) = with(binding) {
    tvListName.text = value.userList.name
    tvListProductCount.text = "${value.products.size}"
    tvListUncompletedItems.text = value.wrappedUncompletedItems()
    bindListQuantity(value.userList.color)
    binding.root.setSafeOnClickListener {
      onClickUserList.invoke(value)
    }
  }

  private fun bindListQuantity(color: String) = with(binding.cvListProductQuantity) {
    val backgroundTintList = try {
      ColorStateList.valueOf(Color.parseColor(color))
    } catch (e: Exception) {
      ColorStateList.valueOf(itemView.context.compatColor(R.color.colorBlue))
    }
    setCardBackgroundColor(backgroundTintList)
  }
}