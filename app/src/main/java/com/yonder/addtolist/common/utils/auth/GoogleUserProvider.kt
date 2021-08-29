package com.yonder.addtolist.common.utils.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.core.network.UserRegisterRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 31/05/2021
 */
class GoogleUserProvider @Inject constructor(
  @ApplicationContext override val context: Context
) : UserProvider() {

   fun create(account: GoogleSignInAccount): UserRegisterRequest {
    val email = account.email
    val firstName = account.givenName
    val lastName = account.familyName
    val photoUrl = account.photoUrl.toString()
    val userId = account.id
    return createUserRegisterRequest(
      context,
      ProviderType.GOOGLE,
      firstName,
      lastName,
      email,
      photoUrl,
      userId
    )
  }

}
