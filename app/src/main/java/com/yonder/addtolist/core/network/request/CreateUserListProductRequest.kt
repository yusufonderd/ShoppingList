package com.yonder.addtolist.core.network.request

import com.google.gson.annotations.SerializedName
import com.yonder.addtolist.local.entity.CATEGORY_OTHER_IMAGE
import com.yonder.addtolist.local.entity.CATEGORY_OTHER_NAME

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */

data class CreateUserListProductRequest(
  @SerializedName("user_list_id") val userListId: String,
  @SerializedName("name") val name: String,
  @SerializedName("quantity") val quantity: Double = 1.0,
  @SerializedName("price") val price: Double = 0.0,
  @SerializedName("total_price") val total_price: Double = 0.0,
  @SerializedName("unit") val unit: String = "",
  @SerializedName("category_name") val category_name: String = CATEGORY_OTHER_NAME,
  @SerializedName("category_image") val category_image: String = CATEGORY_OTHER_IMAGE,
  @SerializedName("note") val note: String = "",
  @SerializedName("done") val done: Boolean = false,
  @SerializedName("favorite") val favorite: Boolean = false
)


data class UserListProductRequest(
  @SerializedName("name") val name: String,
  @SerializedName("quantity") val quantity: Double = 0.0,
  @SerializedName("price") val price: Double = 0.0,
  @SerializedName("total_price") val total_price: Double = 0.0,
  @SerializedName("unit") val unit: String = "",
  @SerializedName("user_list_id") val userListId: String = "",
  @SerializedName("category_name") val category_name: String = CATEGORY_OTHER_NAME,
  @SerializedName("category_image") val category_image: String = CATEGORY_OTHER_IMAGE,
  @SerializedName("note") val note: String = "",
  @SerializedName("done") val done: Boolean = false,
  @SerializedName("favorite") val favorite: Boolean = false
)