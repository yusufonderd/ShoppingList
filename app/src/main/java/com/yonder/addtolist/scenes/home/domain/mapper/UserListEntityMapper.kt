package com.yonder.addtolist.scenes.home.domain.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.scenes.home.domain.model.UserListDataModel
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */
class UserListEntityMapper @Inject constructor() : Mapper<UserListEntity?, UserListDataModel> {
  override fun map(input: UserListEntity?) = UserListDataModel(input?.name.orEmpty())
}