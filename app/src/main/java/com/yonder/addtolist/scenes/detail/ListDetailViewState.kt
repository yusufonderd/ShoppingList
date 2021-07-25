package com.yonder.addtolist.scenes.detail

import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListWithProducts

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
sealed class ListDetailViewState {
  object Initial : ListDetailViewState()
  object Loading : ListDetailViewState()
  data class UserListContent(
    val userListWithProducts: UserListWithProducts,
    val list: List<ProductEntitySummary>,
    val query: String
  ) : ListDetailViewState()
  object OpenKeyboard : ListDetailViewState()

  data class Error(var errorMessage: String) : ListDetailViewState()
}
