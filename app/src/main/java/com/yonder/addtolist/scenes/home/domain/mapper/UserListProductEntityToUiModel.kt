package com.yonder.addtolist.scenes.home.domain.mapper

import android.content.Context
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.home.domain.wrapper.ProductQuantityWrapper
import com.yonder.addtolist.scenes.productdetail.model.enums.DoneType
import com.yonder.addtolist.scenes.productdetail.model.enums.FavoriteType
import com.yonder.addtolist.scenes.productdetail.model.wrapper.CategoryImageWrapper
import com.yonder.addtolist.scenes.productdetail.model.wrapper.CategoryNameWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

class UserListProductEntityToUiModel @Inject constructor(@ApplicationContext private val context: Context) :
  Mapper<UserListProductEntity, UserListProductUiModel> {
  override fun map(input: UserListProductEntity): UserListProductUiModel {
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
