package com.yonder.addtolist.scenes.home.data.remote.response

import com.yonder.addtolist.core.network.responses.UserResponse

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */
data class UserListResponse(
  val id: Int,
  val visible: Int,
  val name: String,
  val reminderAt: String,
  val uuid: String,
  val createdAt: String,
  val color: String,
  val user: UserResponse,
  val userListProducts: List<UserListProductResponse>
)
