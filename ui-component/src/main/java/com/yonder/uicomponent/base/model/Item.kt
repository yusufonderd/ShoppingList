package com.yonder.uicomponent.base.model

import android.os.Parcelable

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

@kotlinx.parcelize.Parcelize
data class Item(
  val id: Int?,
  val name: String,
  val quantity: Double,
  val isDone: Boolean,
  val isFavorite: Boolean,
  val note: String,
  val categoryImage: String,
) : Parcelable