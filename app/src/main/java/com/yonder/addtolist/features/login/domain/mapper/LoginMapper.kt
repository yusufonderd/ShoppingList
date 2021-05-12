package com.yonder.addtolist.features.login.domain.mapper

import com.yonder.addtolist.core.base.BaseMapper
import com.yonder.addtolist.core.base.BaseResponse
import com.yonder.addtolist.features.login.domain.decider.LoginDecider
import com.yonder.addtolist.features.login.data.remote.response.UserResponse
import com.yonder.addtolist.features.login.domain.model.UserUiModel
import javax.inject.Inject

/**
 * Yusuf Onder on 09,May,2021
 */

class LoginMapper @Inject constructor(private val decider: LoginDecider) :
  BaseMapper<BaseResponse<UserResponse>, UserUiModel> {

  override fun map(input: BaseResponse<UserResponse>): UserUiModel {
    return UserUiModel(
      result = decider.toBaseUiResult(input),
      token = input.data?.apiToken.orEmpty()
    )
  }
}