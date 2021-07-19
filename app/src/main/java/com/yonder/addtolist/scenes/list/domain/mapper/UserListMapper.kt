package com.yonder.addtolist.scenes.list.domain.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.scenes.list.data.remote.response.UserListResponse
import com.yonder.addtolist.scenes.list.domain.model.UserListDataModel
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */
class UserListResponseMapper @Inject constructor() : Mapper<UserListResponse?, UserListDataModel> {
  override fun map(input: UserListResponse?): UserListDataModel {
    return UserListDataModel(input?.name.orEmpty())
  }
}



class UserListMapper @Inject constructor() : Mapper<UserListResponse?, UserListEntity> {
  override fun map(input: UserListResponse?): UserListEntity {
    return UserListEntity(input?.id.toString(),input?.name.orEmpty())
  }
}


class UserListEntityMapper @Inject constructor() : Mapper<UserListEntity?, UserListDataModel> {
  override fun map(input: UserListEntity?) = UserListDataModel(input?.name.orEmpty())
}

