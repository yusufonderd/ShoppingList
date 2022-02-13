package com.yonder.addtolist.domain.decider

import com.yonder.addtolist.core.extensions.SPACE_STRING

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */
object CategoryNameWrapper {

  operator fun invoke(categoryImage: String, categoryName: String): String {
    val wrappedCategoryImage = CategoryImageWrapper.invoke(categoryImage)
    val stringBuilder = StringBuilder()
    stringBuilder.append(wrappedCategoryImage)
    stringBuilder.append(SPACE_STRING)
    stringBuilder.append(categoryName)
    return stringBuilder.toString()
  }

}
