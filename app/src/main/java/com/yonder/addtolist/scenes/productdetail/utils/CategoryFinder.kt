package com.yonder.addtolist.scenes.productdetail.utils

import com.yonder.addtolist.local.entity.CategoryEntity

/**
 * @author yusuf.onder
 * Created on 29.08.2021
 */

class CategoryFinder(var categories: List<CategoryEntity>) {

  fun find(categoryImage: String?): CategoryEntity? {
    return categories.find { it.image == categoryImage }
  }

}
