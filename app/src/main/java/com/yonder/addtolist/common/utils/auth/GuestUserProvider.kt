package com.yonder.addtolist.common.utils.auth

import android.content.Context
import com.yonder.addtolist.common.ProviderType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 31/05/2021
 */
class GuestUserProvider @Inject constructor(
  @ApplicationContext override val context: Context
) : UserProvider() {

  fun create() = createUserRegisterRequest(context, ProviderType.GUEST)

}