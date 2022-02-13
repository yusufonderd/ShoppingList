package com.yonder.addtolist.domain.mapper

import com.yonder.addtolist.common.enums.ProviderType
import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.core.network.responses.UserResponse
import com.yonder.addtolist.domain.uimodel.UserUiModel
import java.lang.StringBuilder
import javax.inject.Inject

/**
 * Yusuf Onder on 09,May,2021
 */

class LoginMapper @Inject constructor() :
  Mapper<BaseResponse<UserResponse>, UserUiModel> {

  override fun map(input: BaseResponse<UserResponse>): UserUiModel {
    return UserUiModel(
      result = input.toBaseUiResult(),
      token = input.data?.apiToken,
      firstName = input.data?.firstName.orEmpty(),
      lastName = input.data?.lastName.orEmpty(),
      profileImage = input.data?.photoUrl.orEmpty(),
      isPremium = input.data?.premium != 1,
      email = input.data?.email.orEmpty(),
      fullName = provideFullName(input.data),
      createdAt = input.data?.createdAt.orEmpty(),
      providerType = ProviderType.init(input.data?.providerType ?: "guest")
    )
  }

  private fun provideFullName(userResponse: UserResponse?): String {
    val stringBuilder = StringBuilder()
    userResponse?.firstName?.let {
      stringBuilder.append(it)
      stringBuilder.append(" ")
    }
    userResponse?.lastName?.let(stringBuilder::append)
    return stringBuilder.toString()
  }
}
