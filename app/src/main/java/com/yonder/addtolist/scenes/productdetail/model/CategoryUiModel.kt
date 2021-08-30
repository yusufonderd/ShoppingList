package com.yonder.addtolist.scenes.productdetail.model

import android.os.Parcelable
import com.yonder.addtolist.scenes.productdetail.model.wrapper.CategoryNameWrapper
import kotlinx.parcelize.Parcelize

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

@Parcelize
data class CategoryUiModel(
  val name: String,
  val image: String
) : Parcelable {
  val wrappedName: String = CategoryNameWrapper(image, name)
}
