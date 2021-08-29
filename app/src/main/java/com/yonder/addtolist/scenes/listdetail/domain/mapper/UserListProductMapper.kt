package com.yonder.addtolist.scenes.listdetail.domain.mapper

import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.core.extensions.toBoolean
import com.yonder.addtolist.core.extensions.toInt
import com.yonder.addtolist.core.network.request.CreateUserListProductRequest
import com.yonder.addtolist.core.network.request.UserListProductRequest
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.home.data.remote.response.UserListProductResponse

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
object UserListProductMapper {

  fun mapEntityToResponse(listId: String,input: UserListProductEntity): UserListProductRequest {
    return UserListProductRequest(
      name = input.name.orEmpty(),
      userListId = listId,
      category_image = input.categoryImage.orEmpty(),
      category_name = input.categoryName.orEmpty(),
      note = input.note.orEmpty(),
      unit = input.unit.orEmpty(),
      done = input.done.toBoolean(),
      favorite = input.favorite.toBoolean(),
      quantity = input.quantity.orZero(),
      price = input.price.orZero(),
    )
  }

  fun mapResponseToEntity(listUUID: String, input: UserListProductResponse): UserListProductEntity {
    return UserListProductEntity(
      listUUID = listUUID,
      name = input.name,
      categoryName = input.categoryName,
      categoryImage = input.categoryImage,
      id = input.id,
      note = input.note,
      unit = input.unit,
      done = input.done.toInt(),
      favorite = input.favorite.toInt(),
      quantity = input.quantity,
      price = input.price,
      sync = true
    )
  }

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
    listId: String?,
    productName: String,
    productCategoryImage: String,
  ): CreateUserListProductRequest {
    return CreateUserListProductRequest(
      userListId = listId.orEmpty(),
      name = productName,
      category_image = productCategoryImage
    )
  }
}
