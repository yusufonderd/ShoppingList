package com.yonder.addtolist.scenes.list.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */
data class UserListProductResponse(
  @SerializedName("id") val id: Int,
  @SerializedName("name") val name: String,
  @SerializedName("quantity") val quantity: Double,
  @SerializedName("favorite") val favorite: Boolean,
  @SerializedName("price") val price: Double,
  @SerializedName("total_price") val totalPrice: Double,
  @SerializedName("unit") val unit: String,
  @SerializedName("category_image") val categoryImage: String,
  @SerializedName("category_name") val categoryName: String,
  @SerializedName("done") val done: Boolean,
  @SerializedName("note") val note: String,
  @SerializedName("created_at") val createdAt: String,
  @SerializedName("updated_at") val updatedAt: String,
  @SerializedName("done_at") val doneAt: String
)
