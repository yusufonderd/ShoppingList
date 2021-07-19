package com.yonder.addtolist.scenes.login.domain.mapper

import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.core.network.responses.UserResponse
import com.yonder.addtolist.scenes.login.domain.model.UserUiModel
import javax.inject.Inject

/**
 * Yusuf Onder on 09,May,2021
 */

class LoginMapper @Inject constructor() :
  Mapper<BaseResponse<UserResponse>, UserUiModel> {

  override fun map(input: BaseResponse<UserResponse>): UserUiModel {
    return UserUiModel(
      result = input.toBaseUiResult(),
      token = input.data?.apiToken
    )
  }
}