package com.yonder.addtolist.scenes.productdetail.utils

import com.yonder.addtolist.scenes.home.domain.model.CategoryUiModel

/**
 * @author yusuf.onder
 * Created on 29.08.2021
 */

class CategoryFinder(var categories: List<CategoryUiModel>) {

  fun find(categoryImage: String?): CategoryUiModel? {
    return categories.find { it.image == categoryImage }
  }

}
