package com.yonder.addtolist.features.list.data.remote.response

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */
data class UserListProductResponse(
  val id: Int,
  val name: String,
  val quantity: Double,
  val favorite : Int,
  val price: Double,
  val totalPrice: Double,
  val unit : String,
  val categoryImage: String,
  val categoryName: String,
  val done : Int,
  val note : String,
  val createdAt : String,
  val updatedAt : String,
  val doneAt :String
)
