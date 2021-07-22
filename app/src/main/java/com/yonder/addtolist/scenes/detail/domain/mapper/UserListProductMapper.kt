package com.yonder.addtolist.scenes.detail.domain.mapper

import com.yonder.addtolist.core.extensions.orFalse
import com.yonder.addtolist.core.extensions.toInt
import com.yonder.addtolist.core.network.request.CreateUserListProductRequest
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.list.data.remote.response.UserListProductResponse

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
object UserListProductMapper {

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

  fun mapProductEntitySummaryToRequest(product: ProductEntitySummary): CreateUserListProductRequest {
    return CreateUserListProductRequest(
      name = product.name,
      category_image = product.categoryImage.orEmpty()
    )
  }
}