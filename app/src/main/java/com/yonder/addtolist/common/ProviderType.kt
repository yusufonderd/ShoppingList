package com.yonder.addtolist.common

/**
 * Yusuf Onder on 09,May,2021
 */

enum class ProviderType(val value: String) {
  FACEBOOK("facebook"),
  GOOGLE("google"),
  GUEST("guest"),
  UNKNOWN("unknown");

  companion object {
    fun init(value: String?): ProviderType = values().find { it.value == value } ?: UNKNOWN
  }
}
