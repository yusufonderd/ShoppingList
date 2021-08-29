package com.yonder.addtolist.common.utils.auth

import android.content.Context
import android.os.Build
import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.common.utils.device.DeviceUtils
import com.yonder.addtolist.core.network.UserRegisterRequest

/**
 * @author: yusufonder
 * @date: 31/05/2021
 */
abstract class UserProvider {

  abstract val context: Context

  @Suppress("LongParameterList")
  fun createUserRegisterRequest(
    context: Context,
    providerType: ProviderType,
    firstName: String? = "",
    lastName: String? = "",
    email: String? = "",
    photoUrl: String = "",
    userId: String? = "",
    deviceUUID: String = ""
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
      deviceUUID = deviceUUID,
      fcmToken = "",
    )
  }

}
