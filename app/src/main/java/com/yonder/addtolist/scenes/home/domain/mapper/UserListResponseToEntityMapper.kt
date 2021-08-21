package com.yonder.addtolist.scenes.home.domain.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.core.network.request.CreateUserListRequest
import com.yonder.addtolist.scenes.home.data.remote.response.UserListResponse
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */

class UserListResponseToEntityMapper @Inject constructor() : Mapper<UserListResponse?, UserListEntity> {
  override fun map(input: UserListResponse?): UserListEntity {
    return UserListEntity(
      id = input?.id,
      uuid = input?.uuid.orEmpty(),
      name = input?.name.orEmpty(),
      sync = true,
      color = input?.color.orEmpty()
    )
  }
}




