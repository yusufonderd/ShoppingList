package com.yonder.addtolist.domain.usecase

import android.os.Bundle
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import org.json.JSONObject
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class FacebookGraphUseCaseImpl @Inject constructor() : FacebookGraphUseCase {
  override fun getUserInfo(
    loginResult: LoginResult,
    invoker: (userInfoObject: JSONObject) -> Unit
  ) {
    val request = GraphRequest.newMeRequest(loginResult.accessToken) { userInfoObject, _ ->
      invoker.invoke(userInfoObject)
    }
    val parameters = Bundle()
    parameters.putString(FIELDS_KEY, FIELDS_VALUES)
    request.parameters = parameters
    request.executeAsync()
  }

  companion object {
    const val FIELDS_KEY = "fields"
    const val FIELDS_VALUES = "id,first_name,last_name,email"
  }
}
