package com.yonder.addtolist.domain.uimodel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

interface ItemUiModelType {
  val name: String
}

data class ItemUiModel(
  override val name: String,
  var product: UserListProductUiModel? = null
) : ItemUiModelType
