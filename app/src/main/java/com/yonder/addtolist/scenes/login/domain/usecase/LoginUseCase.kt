package com.yonder.addtolist.scenes.login.domain.usecase

import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.core.network.UserRegisterRequest
import com.yonder.addtolist.scenes.login.domain.model.UserUiModel
import kotlinx.coroutines.flow.Flow

/**
 * Yusuf Onder on 12,May,2021
 */

interface LoginUseCase {
  fun login(userRegisterParam: UserRegisterRequest): Flow<Result<UserUiModel>>
}