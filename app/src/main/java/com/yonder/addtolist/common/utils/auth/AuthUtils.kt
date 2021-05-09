package com.yonder.addtolist.common.utils.auth

import android.content.Context
import android.os.Build
import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.common.utils.device.DeviceUtils
import com.yonder.addtolist.domain.model.UserRegisterParam

/**
 * Yusuf Onder on 09,May,2021
 */

object AuthUtils {

  fun getGuestUserParams(
    context: Context,
    providerType: ProviderType,
    gcmToken: String?,
    deviceUUID: String
  ): UserRegisterParam {
    val versionName: String = BuildConfig.VERSION_NAME
    val version = Build.VERSION.SDK_INT
    val deviceToken = gcmToken.orEmpty()
    val deviceModel = DeviceUtils.getModel()
    val phoneLanguage = DeviceUtils.getLocalLanguage()
    val region = DeviceUtils.getRegion(context)
    return UserRegisterParam(
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