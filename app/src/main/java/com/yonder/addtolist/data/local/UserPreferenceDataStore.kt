package com.yonder.addtolist.data.local

/**
 * Yusuf Onder on 07,May,2021
 */

interface UserPreferenceDataStore {
  fun getAppLanguageId() : Int
  fun setAppLanguageId(languageId: Int)
  fun getUUID(): String?
  fun getToken(): String?
  fun saveUUID(uuid: String)
  fun saveToken(token: String?)
  fun isFetchedCategoriesAndProducts(): Boolean
  fun setFetchedCategoriesAndProducts()
}