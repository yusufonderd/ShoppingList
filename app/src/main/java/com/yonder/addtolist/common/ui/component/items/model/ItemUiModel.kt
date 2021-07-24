package com.yonder.addtolist.common.ui.component.items.model

import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

interface ItemUiModelType {
  val name: String
}

data class ItemUiModel(
  override val name: String,
  var entity: UserListProductEntity? = null
) : ItemUiModelType