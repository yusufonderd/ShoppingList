package com.yonder.addtolist.scenes.list.domain.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.list.data.remote.response.UserListProductResponse

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */

class UserListProductMapper constructor(
  private val listUUID: String
) : Mapper<UserListProductResponse?, UserListProductEntity> {

  override fun map(input: UserListProductResponse?): UserListProductEntity {
    return UserListProductEntity(
      listUUID,
      input?.name,
      input?.categoryImage,
      input?.categoryName,
      input?.id,
      input?.note,
      input?.unit,
      input?.done,
      input?.favorite,
      input?.quantity,
      input?.price,
      true
    )
  }
}
