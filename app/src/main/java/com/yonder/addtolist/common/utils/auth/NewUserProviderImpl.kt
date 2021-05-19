package com.yonder.addtolist.common.utils.auth

import android.content.Context
import android.os.Build
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.common.utils.device.DeviceUtils
import com.yonder.addtolist.features.login.data.remote.request.UserRegisterRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject

/**
 * Yusuf Onder on 09,May,2021
 */

class NewUserProviderImpl @Inject constructor(@ApplicationContext val context: Context) :
  NewUserProvider {

  private fun createUserRegisterRequest(
    providerType: ProviderType,
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
      deviceModel = deviceModel,
      appVersion = appVersion,
      language = language,
      system_version = systemVersion,
      region = region,
      firstName = firstName,
      lastName = lastName,
      email = email,
      photoUrl = photoUrl,
      userId = userId,
      deviceUUID = "",
      fcmToken = "",
    )
  }


  override fun createUserRegisterRequest(
    providerType: ProviderType
  ) = createUserRegisterRequest(providerType, "", "", "", "", "")


  override fun createUserRegisterRequest(
    providerType: ProviderType,
    jsonObject: JSONObject
  ): UserRegisterRequest {
    val userId = jsonObject.getString("id")
    val firstName = jsonObject.getString("first_name")
    val lastName = jsonObject.getString("last_name")
    val email = jsonObject.getString("email")
    val photoUrl = "https://graph.facebook.com/$userId/picture?type=large"
    return createUserRegisterRequest(
      providerType,
      firstName,
      lastName,
      email,
      photoUrl,
      userId
    )
  }

  override fun createUserRegisterRequest(
    providerType: ProviderType,
    account: GoogleSignInAccount
  ): UserRegisterRequest {
    val email = account.email
    val firstName = account.givenName
    val lastName = account.familyName
    val photoUrl = account.photoUrl.toString()
    val userId = account.id
    return createUserRegisterRequest(
      providerType,
      firstName,
      lastName,
      email,
      photoUrl,
      userId
    )
  }

}