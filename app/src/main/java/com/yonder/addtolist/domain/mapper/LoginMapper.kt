package com.yonder.addtolist.domain.mapper

import com.yonder.addtolist.core.base.BaseMapper
import com.yonder.addtolist.core.BaseResponse
import com.yonder.addtolist.domain.decider.LoginDecider
import com.yonder.addtolist.domain.model.response.UserResponse
import com.yonder.addtolist.domain.model.ui.UserUiModel
import javax.inject.Inject

/**
 * Yusuf Onder on 09,May,2021
 */

class LoginMapper @Inject constructor(private val decider: LoginDecider) :
  BaseMapper<BaseResponse<UserResponse>, UserUiModel> {

  override fun map(input: BaseResponse<UserResponse>): UserUiModel {
    return UserUiModel(result = input.toBaseUiResult(),token = input.data?.apiToken.orEmpty())
  }
}