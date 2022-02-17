package com.yonder.addtolist.domain.decider

import com.yonder.addtolist.domain.uimodel.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 17.02.2022
 */

object CompletedItemsWrapper {

    fun getCount(products: List<UserListProductUiModel>?): Int {
        return products
            .orEmpty()
            .count { it.isDone }
    }

}