package com.yonder.addtolist.common.utils.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import org.json.JSONObject

/**
 * Yusuf Onder on 11,May,2021
 */

interface NewUserProvider {

  /** Guest User **/
  fun createUserRegisterRequest(
    providerType: ProviderType
  ): UserRegisterRequest

  /** Facebook User **/
  fun createUserRegisterRequest(
    providerType: ProviderType,
    jsonObject: JSONObject
  ): UserRegisterRequest

  /** Google User **/
  fun createUserRegisterRequest(
    providerType: ProviderType,
    account: GoogleSignInAccount
  ): UserRegisterRequest

}