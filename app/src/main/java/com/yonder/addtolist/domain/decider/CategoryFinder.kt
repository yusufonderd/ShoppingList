package com.yonder.addtolist.domain.decider

import com.yonder.addtolist.domain.uimodel.CategoryUiModel

/**
 * @author yusuf.onder
 * Created on 29.08.2021
 */

class CategoryFinder(var categories: List<CategoryUiModel>) {

  fun find(categoryImage: String?): CategoryUiModel? {
    return categories.find { it.image == categoryImage }
  }

}
