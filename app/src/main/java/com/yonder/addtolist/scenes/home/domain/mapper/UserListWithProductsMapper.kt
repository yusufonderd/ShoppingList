package com.yonder.addtolist.scenes.home.domain.mapper

import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.home.domain.model.UserListUiModel
import com.yonder.addtolist.scenes.home.domain.wrapper.UncompletedItemsWrapper
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */


class UserListWithProductsMapper @Inject constructor() :
  Mapper<UserListWithProducts, UserListUiModel> {

  val mapper = UserListProductEntityToUiModel()
  override fun map(input: UserListWithProducts): UserListUiModel {
    val productsList = ListMapperImpl(mapper).map(input.products)
    return UserListUiModel(
      id = input.userList.id.orZero(),
      uuid = input.userList.uuid,
      name = input.userList.name,
      color = input.userList.color,
      uncompletedItems = UncompletedItemsWrapper.wrap(productsList),
      products = ListMapperImpl(mapper).map(input.products)
    )
  }

}
