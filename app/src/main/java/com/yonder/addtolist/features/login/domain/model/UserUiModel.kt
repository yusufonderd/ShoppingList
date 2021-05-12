package com.yonder.addtolist.features.login.domain.model

import com.yonder.addtolist.core.base.BaseUiModel
import com.yonder.addtolist.core.base.BaseUiResult

/**
 * Yusuf Onder on 09,May,2021
 */

data class UserUiModel(
  override val result: BaseUiResult,
  var token: String
) : BaseUiModel()