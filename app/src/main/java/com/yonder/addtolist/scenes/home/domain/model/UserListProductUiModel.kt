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
  val name: String,
  val note: String,
  val categoryImage: String,
  val quantity: String,
  val quantityValue: Double,
  val price: String,
  val unit: String,
  val isFavorite: Boolean,
  val isDone: Boolean
) : Parcelable
