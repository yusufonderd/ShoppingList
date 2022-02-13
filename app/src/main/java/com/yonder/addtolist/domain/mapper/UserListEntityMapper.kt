package com.yonder.addtolist.domain.mapper

import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.domain.uimodel.UserListDataModel
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */
class UserListEntityMapper @Inject constructor() : Mapper<UserListEntity?, UserListDataModel> {
  override fun map(input: UserListEntity?) = UserListDataModel(input?.name.orEmpty())
}
