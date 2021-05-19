package com.yonder.addtolist.data.local

/**
 * Yusuf Onder on 07,May,2021
 */

interface UserPreferenceDataStore {
  val uuid: String?
  val token: String?
  fun saveUUID(uuid: String)
  fun saveToken(token: String)
}