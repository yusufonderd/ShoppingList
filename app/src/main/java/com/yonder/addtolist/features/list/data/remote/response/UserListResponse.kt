package com.yonder.addtolist.features.list.data.remote.response

import com.yonder.addtolist.features.login.data.remote.response.UserResponse

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */
data class UserListResponse(
  val id: Int,
  val visible: Boolean,
  val name: String,
  val reminderAt: String,
  val uuid: String,
  val createdAt: String,
  val color: String,
  val user: UserResponse,
  val userListProducts: List<UserListProductResponse>
)