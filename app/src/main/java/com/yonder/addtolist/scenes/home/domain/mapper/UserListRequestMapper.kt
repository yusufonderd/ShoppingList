package com.yonder.addtolist.scenes.home.domain.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.local.entity.UserListEntity
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 21.08.2021
 */
class UserListRequestMapper @Inject constructor() :
  Mapper<CreateUserListRequest?, UserListEntity> {
  override fun map(input: CreateUserListRequest?): UserListEntity {
    return UserListEntity(
      id = null,
      uuid = input?.uuid.orEmpty(),
      name = input?.name.orEmpty(),
      sync = false,
      color = input?.color.orEmpty(),
      )
  }
}
