package com.yonder.addtolist.core.network.responses

import com.google.gson.annotations.SerializedName

/**
 * Yusuf Onder on 09,May,2021
 */

data class UserResponse(
  @SerializedName("id") var id: Int?,
  @SerializedName("first_name") var firstName: String?,
  @SerializedName("last_name") var lastName: String?,
  @SerializedName("api_token") var apiToken: String?,
  @SerializedName("email") var email: String?,
  @SerializedName("provider_type") var providerType: String?,
  @SerializedName("premium") var premium: Int?,
  @SerializedName("photo_url") var photoUrl: String?,
  @SerializedName("created_at") var createdAt: String?,
  @SerializedName("device_uuid") var deviceUUID: String?
)
