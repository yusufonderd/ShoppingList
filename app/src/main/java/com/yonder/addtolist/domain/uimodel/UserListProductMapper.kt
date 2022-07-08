package com.yonder.addtolist.domain.uimodel

import com.yonder.addtolist.core.extensions.toInt
import com.yonder.addtolist.core.network.request.CreateUserListProductRequest
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */


object UserListProductMapper {

  fun mapRequestToEntity(
    listUUID: String,
    input: CreateUserListProductRequest
  ): UserListProductEntity {
    return UserListProductEntity(
      id = null,
      listUUID = listUUID,
      name = input.name,
      categoryName = input.category_name,
      categoryImage = input.category_image,
      note = input.note,
      unit = input.unit,
      done = input.done.toInt(),
      favorite = input.favorite.toInt(),
      quantity = input.quantity,
      price = input.price,
      sync = false
    )
  }

  fun mapProductEntitySummaryToRequest(

    productName: String,
    productCategoryImage: String,
  ): CreateUserListProductRequest {
    return CreateUserListProductRequest(
      name = productName,
      category_image = productCategoryImage
    )
  }
}
