package com.yonder.addtolist.scenes.login.domain.model

import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.core.network.responses.BaseUiModel
import com.yonder.addtolist.core.network.responses.BaseUiResult

/**
 * Yusuf Onder on 09,May,2021
 */

data class UserUiModel(
  override val result: BaseUiResult,
  val token: String?,
  val email: String,
  val profileImage: String,
  val firstName: String,
  val lastName: String,
  val isPremium: Boolean,
  val fullName: String,
  val createdAt : String,
  val providerType : ProviderType
) : BaseUiModel(result){

  fun isAnonymousUser(): Boolean{
    return providerType == ProviderType.UNKNOWN || providerType == ProviderType.GUEST
  }
}

