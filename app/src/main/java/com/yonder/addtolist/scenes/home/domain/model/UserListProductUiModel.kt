package com.yonder.addtolist.scenes.home.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

@Parcelize
data class UserListProductUiModel(
  val id: Int?,
  val listUUID: String,
  val name: String,
  val note: String,
  val categoryUnicode: String,
  val categoryImage: String,
  val quantity: String,
  val quantityValue: Double,
  val price: String,
  val priceValue: Double,
  val unit: String,
  val isFavorite: Boolean,
  val isDone: Boolean
) : Parcelable
