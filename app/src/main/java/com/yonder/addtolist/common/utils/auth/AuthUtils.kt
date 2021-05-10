package com.yonder.addtolist.common.utils.auth

import android.content.Context
import android.os.Build
import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.common.utils.device.DeviceUtils
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import javax.inject.Inject

/**
 * Yusuf Onder on 09,May,2021
 */

interface IAuthUtils {
  fun getGuestUserParams(providerType: ProviderType, gcmToken: String?, deviceUUID: String): UserRegisterRequest
}

class AuthUtils @Inject constructor(val context: Context) : IAuthUtils {

  override fun getGuestUserParams(
    providerType: ProviderType,
    gcmToken: String?,
    deviceUUID: String
  ): UserRegisterRequest {
    val versionName: String = BuildConfig.VERSION_NAME
    val version = Build.VERSION.SDK_INT
    val deviceToken = gcmToken.orEmpty()
    val deviceModel = DeviceUtils.getModel()
    val phoneLanguage = DeviceUtils.getLocalLanguage()
    val region = DeviceUtils.getRegion(context)
    return UserRegisterRequest(
      providerType = providerType.value,
      deviceUUID = deviceUUID,
      deviceModel = deviceModel,
      appVersion = versionName,
      fcmToken = deviceToken,
      language = phoneLanguage,
      system_version = "$version",
      region = region
    )
  }

}