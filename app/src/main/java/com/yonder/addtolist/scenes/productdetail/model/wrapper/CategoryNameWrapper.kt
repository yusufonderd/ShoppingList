package com.yonder.addtolist.scenes.productdetail.model.wrapper

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */
object CategoryNameWrapper {

  fun wrap(categoryImage: String, categoryName: String): String {
    val wrappedCategoryImage = CategoryImageWrapper.wrap(categoryImage)
    val stringBuilder = StringBuilder()
    stringBuilder.append(wrappedCategoryImage)
    stringBuilder.append(" ")
    stringBuilder.append(categoryName)
    return stringBuilder.toString()
  }

}