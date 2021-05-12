package com.yonder.addtolist.features.login.domain.usecase

import com.facebook.login.LoginResult
import org.json.JSONObject

/**
 * Yusuf Onder on 12,May,2021
 */

interface  FacebookGraphUseCase {
  fun getUserInfo(loginResult: LoginResult, invoker: (userInfoObject: JSONObject) -> Unit)
}