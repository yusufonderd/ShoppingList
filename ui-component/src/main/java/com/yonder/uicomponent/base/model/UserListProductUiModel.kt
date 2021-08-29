package com.yonder.uicomponent.base.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

@Parcelize
data class UserListProductUiModel(
  val id: Int?,
  val name: String,
  val quantity: Double,
  val isDone: Boolean,
  val isFavorite: Boolean,
  val note: String,
  val categoryImage: String,
) : Parcelable