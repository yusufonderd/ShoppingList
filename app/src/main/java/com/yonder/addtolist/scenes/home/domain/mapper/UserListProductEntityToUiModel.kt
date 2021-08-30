package com.yonder.addtolist.scenes.home.domain.mapper

import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.productdetail.model.enums.DoneType
import com.yonder.addtolist.scenes.productdetail.model.enums.FavoriteType
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

class UserListProductEntityToUiModel @Inject constructor() :
  Mapper<UserListProductEntity, UserListProductUiModel> {
  override fun map(input: UserListProductEntity): UserListProductUiModel {
    return UserListProductUiModel(
      id = input.id,
      name = input.name.orEmpty(),
      isFavorite = input.favorite == FavoriteType.Favorite.value,
      isDone = input.done == DoneType.Done.value,
      note = input.note.orEmpty(),
      unit = input.unit.orEmpty(),
      price = input.price.orZero().toString(),
      quantity = input.quantity.orZero().toString(),
      quantityValue = input.quantity.orZero(),
      categoryImage = input.categoryImage.orEmpty()
    )
  }
}
