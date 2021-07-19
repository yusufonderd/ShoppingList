package com.yonder.addtolist.scenes.list.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.databinding.ItemUserListBinding
import com.yonder.addtolist.local.entity.UserListEntity

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

class UserListViewHolder(
  view: View,
  private val onClickUserList: ((value: UserListEntity) -> Unit)
) : RecyclerView.ViewHolder(view) {
  private val binding = ItemUserListBinding.bind(view)

  fun bind(value: UserListEntity) = with(binding) {
    binding.tvListName.text = value.name
    binding.root.setSafeOnClickListener {
      onClickUserList.invoke(value)
    }
  }
}