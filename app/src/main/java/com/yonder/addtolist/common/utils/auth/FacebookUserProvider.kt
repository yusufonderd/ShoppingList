package com.yonder.addtolist.common.utils.auth

import android.content.Context
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.core.network.UserRegisterRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 31/05/2021
 */
class FacebookUserProvider @Inject constructor(
  @ApplicationContext override val context: Context,
) : UserProvider() {

  fun create(jsonObject: JSONObject): UserRegisterRequest {
    val userId = jsonObject.getString("id")
    val firstName = jsonObject.getString("first_name")
    val lastName = jsonObject.getString("last_name")
    val email = jsonObject.getString("email")
    val photoUrl = "https://graph.facebook.com/$userId/picture?type=large"
    return createUserRegisterRequest(
      context,
      ProviderType.FACEBOOK,
      firstName,
      lastName,
      email,
      photoUrl,
      userId
    )
  }

}
