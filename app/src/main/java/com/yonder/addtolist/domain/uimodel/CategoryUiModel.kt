package com.yonder.addtolist.domain.uimodel

import android.os.Parcelable
import com.yonder.addtolist.domain.decider.CategoryNameWrapper
import kotlinx.parcelize.Parcelize

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

@Parcelize
data class CategoryUiModel(
  val name: String,
  val image: String,
  val formattedName: String = CategoryNameWrapper(image, name)
) : Parcelable
