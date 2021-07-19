package com.yonder.addtolist.scenes.login.domain.model

import com.yonder.addtolist.core.network.responses.BaseUiModel
import com.yonder.addtolist.core.network.responses.BaseUiResult

/**
 * Yusuf Onder on 09,May,2021
 */

data class UserUiModel(
  override val result: BaseUiResult,
  var token: String?
) : BaseUiModel()