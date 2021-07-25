package com.yonder.addtolist.data.local

/**
 * Yusuf Onder on 07,May,2021
 */

interface UserPreferenceDataStore {
  fun getUUID(): String?
  fun getToken(): String?
  fun saveUUID(uuid: String)
  fun saveToken(token: String?)
  fun isFetchedCategories(): Boolean
  fun setFetchedCategories()
}