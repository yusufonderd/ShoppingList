package com.yonder.addtolist.domain.usecase

import com.yonder.core.network.RestResult
import com.yonder.addtolist.core.network.UserRegisterRequest
import com.yonder.addtolist.domain.uimodel.UserUiModel
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface LoginUseCase {
  operator fun invoke(userRegisterParam: UserRegisterRequest): Flow<RestResult<UserUiModel>>
}
