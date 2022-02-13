package com.yonder.addtolist.domain.mapper

import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.data.remote.response.UserListResponse
import com.yonder.addtolist.domain.uimodel.UserListDataModel
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
