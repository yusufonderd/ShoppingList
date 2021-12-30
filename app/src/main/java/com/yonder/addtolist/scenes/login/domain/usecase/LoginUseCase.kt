package com.yonder.addtolist.scenes.login.domain.usecase

import com.yonder.core.network.RestResult
import com.yonder.addtolist.core.network.UserRegisterRequest
import com.yonder.addtolist.scenes.login.domain.model.UserUiModel
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface LoginUseCase {
  operator fun invoke(userRegisterParam: UserRegisterRequest): Flow<RestResult<UserUiModel>>
}
