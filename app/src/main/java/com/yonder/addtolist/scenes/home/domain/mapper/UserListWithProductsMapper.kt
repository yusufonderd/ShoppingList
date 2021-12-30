package com.yonder.addtolist.scenes.home.domain.mapper

import android.content.Context
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.core.base.mapper.ListMapperImpl
import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.home.domain.model.UserListUiModel
import com.yonder.addtolist.scenes.home.domain.wrapper.UncompletedItemsWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */


class UserListWithProductsMapper @Inject constructor(@ApplicationContext private val context: Context) :
  Mapper<UserListWithProducts, UserListUiModel> {

  val mapper = UserListProductEntityToUiModel(context)
  override fun map(input: UserListWithProducts): UserListUiModel {
    val productsList = ListMapperImpl(mapper).map(input.products)
   val safeProductList =  productsList.filterNotNull()
    return UserListUiModel(
      id = input.userList.id.orZero(),
      uuid = input.userList.uuid,
      name = input.userList.name,
      color = input.userList.color,
      uncompletedItems = UncompletedItemsWrapper.wrap(safeProductList),
      products = safeProductList
    )
  }

}
