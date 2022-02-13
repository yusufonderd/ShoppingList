package com.yonder.addtolist.scenes.listdetail

import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.domain.uimodel.ProductEntityUiModel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
sealed class ListDetailViewState {
  object Initial : ListDetailViewState()
  object Loading : ListDetailViewState()
  data class UserListContent(
      val userList: UserListUiModel,
      val list: List<ProductEntityUiModel>,
      val query: String
  ) : ListDetailViewState()
  object OpenKeyboard : ListDetailViewState()

}
