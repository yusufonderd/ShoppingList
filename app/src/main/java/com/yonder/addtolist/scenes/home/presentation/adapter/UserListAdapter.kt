package com.yonder.addtolist.scenes.home.presentation.adapter

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseListAdapter
import com.yonder.addtolist.local.entity.UserListWithProducts

class UserListAdapter(private val onClickUserList: ((value: UserListWithProducts) -> Unit)) :
  BaseListAdapter<UserListWithProducts>(
    itemsSame = { old, new -> old.userList.id == new.userList.id },
    contentsSame = { old, new -> old == new }
  ) {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    viewType: Int
  ): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
    return UserListViewHolder(view, onClickUserList)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is UserListViewHolder -> {
        holder.bind(getItem(position))
      }
    }
  }

}
