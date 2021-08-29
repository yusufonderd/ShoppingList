package com.yonder.addtolist.scenes.productdetail.model

import com.yonder.addtolist.scenes.productdetail.model.wrapper.CategoryImageWrapper
import com.yonder.addtolist.scenes.productdetail.model.wrapper.CategoryNameWrapper

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */
data class CategoryUIModel(
  val name: String,
  val image: String
){
  val wrappedName: String = CategoryNameWrapper.wrap(image, name)
  val wrappedImage : String = CategoryImageWrapper.wrap(image)
}
