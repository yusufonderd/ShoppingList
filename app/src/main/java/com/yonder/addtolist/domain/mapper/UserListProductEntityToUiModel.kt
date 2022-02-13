package com.yonder.addtolist.domain.mapper

import android.content.Context
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.decider.ProductQuantityWrapper
import com.yonder.addtolist.common.enums.DoneType
import com.yonder.addtolist.common.enums.FavoriteType
import com.yonder.addtolist.domain.decider.CategoryImageWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

class UserListProductEntityToUiModel @Inject constructor(@ApplicationContext private val context: Context) :
  Mapper<UserListProductEntity?, UserListProductUiModel?> {
  override fun map(input: UserListProductEntity?): UserListProductUiModel? {
    if (input == null) {
      return null
    }
    return UserListProductUiModel(
      id = input.id,
      listUUID = input.listUUID,
      name = input.name.orEmpty(),
      isFavorite = input.favorite == FavoriteType.Favorite.value,
      isDone = input.done == DoneType.Done.value,
      note = input.note.orEmpty(),
      unit = input.unit.orEmpty(),
      price = input.price.orZero().toString().substringBefore("."),
      priceValue = input.price.orZero(),
      quantity = ProductQuantityWrapper.wrap(
        context,
        input.quantity.orZero(),
        input.unit.orEmpty()
      ),
      quantityValue = input.quantity.orZero(),
      categoryName = input.categoryName.orEmpty(),
      categoryImage = input.categoryImage.orEmpty(),
      categoryUnicode = CategoryImageWrapper.invoke(
        image = input.categoryImage.orEmpty()
      )
    )
  }
}
