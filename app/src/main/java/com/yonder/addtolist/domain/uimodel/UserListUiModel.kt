package com.yonder.addtolist.domain.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

@Parcelize
data class UserListUiModel(
  val id: Int,
  val uuid : String,
  val name: String,
  val color: String,
  val uncompletedItems: String,
  val products: List<UserListProductUiModel>
) : Parcelable
