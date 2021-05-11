package com.yonder.addtolist.common.utils.auth

import android.content.Context
import android.os.Build
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.common.utils.device.DeviceUtils
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import org.json.JSONObject
import javax.inject.Inject

/**
 * Yusuf Onder on 09,May,2021
 */

class NewUserProviderImpl @Inject constructor(val context: Context) : NewUserProvider {

  private fun createUserRegisterRequest(
    providerType: ProviderType,
    deviceUUID: String,
    fcmToken: String,
    firstName: String?,
    lastName: String?,
    email: String?,
    photoUrl: String,
    userId: String?
  ): UserRegisterRequest {
    val deviceModel = DeviceUtils.getModel()
    val appVersion: String = BuildConfig.VERSION_NAME
    val language = DeviceUtils.getLocalLanguage()
    val systemVersion = "${Build.VERSION.SDK_INT}"
    val region = DeviceUtils.getRegion(context)
    return UserRegisterRequest(
      providerType = providerType.value,
      deviceUUID = deviceUUID,
      deviceModel = deviceModel,
      appVersion = appVersion,
      fcmToken = fcmToken,
      language = language,
      system_version = systemVersion,
      region = region,
      firstName = firstName,
      lastName = lastName,
      email = email,
      photoUrl = photoUrl,
      userId = userId
    )
  }


  override fun createUserRegisterRequest(
    providerType: ProviderType,
    gcmToken: String
  ) = createUserRegisterRequest(providerType, "", gcmToken, "", "", "", "", "")


  override fun createUserRegisterRequest(
    providerType: ProviderType,
    token: String,
    jsonObject: JSONObject
  ): UserRegisterRequest {
    val userId = jsonObject.getString("id")
    val firstName = jsonObject.getString("first_name")
    val lastName = jsonObject.getString("last_name")
    val email = jsonObject.getString("email")
    val photoUrl = "https://graph.facebook.com/$userId/picture?type=large"
    return createUserRegisterRequest(
      providerType,
      "",
      token,
      firstName,
      lastName,
      email,
      photoUrl,
      userId
    )
  }

  override fun createUserRegisterRequest(
    providerType: ProviderType,
    token: String,

    account: GoogleSignInAccount
  ): UserRegisterRequest {
    val email = account.email
    val firstName = account.givenName
    val lastName = account.familyName
    val photoUrl = account.photoUrl.toString()
    val userId = account.id
    return createUserRegisterRequest(
      providerType,
      "",
      token,
      firstName,
      lastName,
      email,
      photoUrl,
      userId
    )
  }

}