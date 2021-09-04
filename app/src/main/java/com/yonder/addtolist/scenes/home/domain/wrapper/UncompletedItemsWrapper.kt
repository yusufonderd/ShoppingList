package com.yonder.addtolist.scenes.home.domain.wrapper

import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */
private const val PRODUCTS_SHOWCASE_COUNT = 5

object UncompletedItemsWrapper {

  fun wrap(products: List<UserListProductUiModel>?): String {
    return products
      .orEmpty()
      .filter { !it.isDone }
      .asSequence()
      .take(PRODUCTS_SHOWCASE_COUNT)
      .joinToString(",")
      { it.name }
  }

}
