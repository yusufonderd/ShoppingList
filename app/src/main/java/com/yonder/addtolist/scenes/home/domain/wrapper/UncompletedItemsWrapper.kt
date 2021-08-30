package com.yonder.addtolist.scenes.home.domain.wrapper

import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */
object UncompletedItemsWrapper {

  fun wrap(products: List<UserListProductUiModel>): String {
    return products.filter { !it.isDone }
      .asSequence()
      .take(5)
      .joinToString(",")
      { it.name }
  }

}