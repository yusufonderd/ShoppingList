package com.yonder.addtolist.domain.model.request

import com.google.gson.annotations.SerializedName
import com.yonder.addtolist.BuildConfig

/**
 * Yusuf Onder on 09,May,2021
 */

data class UserRegisterRequest(
  @SerializedName("provider_type") var providerType: String?,
  @SerializedName("device_uuid") var deviceUUID: String,
  @SerializedName("phone_model") var deviceModel: String,
  @SerializedName("app_version") var appVersion: String,
  @SerializedName(BuildConfig.PARAMS_FCM_TOKEN) var fcmToken: String,
  @SerializedName("phone_language") var language: String,
  @SerializedName("system_version") var system_version: String,
  @SerializedName("region") var region: String,
  @SerializedName("first_name") var firstName: String = "",
  @SerializedName("last_name") var lastName: String = "",
  @SerializedName("email") var email: String = "",
  @SerializedName("photo_url") var photoUrl: String = "",
  @SerializedName(BuildConfig.PARAMS_USER_ID) var userId: String = "",
  @SerializedName("platform") var platform: String = "android",
  @SerializedName("premium") var premium: Int = 0
)