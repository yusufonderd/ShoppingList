package com.yonder.addtolist.scenes.list.domain.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.scenes.list.data.remote.response.UserListResponse
import com.yonder.addtolist.scenes.list.domain.model.UserListDataModel
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */

class UserListResponseMapper @Inject constructor() : Mapper<UserListResponse?, UserListDataModel> {
  override fun map(input: UserListResponse?): UserListDataModel {
    return UserListDataModel(input?.name.orEmpty())
  }
}
