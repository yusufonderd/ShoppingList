package com.yonder.addtolist.data.local

import android.content.SharedPreferences
import timber.log.Timber
import javax.inject.Inject

/**
 * Yusuf Onder on 07,May,2021
 */

class UserPreferenceDataStoreImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
  UserPreferenceDataStore {

  override fun saveToken(token: String?) {
    Timber.d("saveToken => $token")
    sharedPreferences.edit().apply {
      putString(KEY_APP_TOKEN, token)
      apply()
    }
  }

  override fun saveUUID(uuid: String) {
    sharedPreferences.edit().apply {
      putString(KEY_UUID, uuid)
      apply()
    }
  }

  override fun setFetchedCategories() {
    sharedPreferences.edit().apply {
      putBoolean(KEY_IS_FETCHED_CATEGORIES, true)
      apply()
    }
  }

  override fun isFetchedCategories(): Boolean {
    return sharedPreferences.getBoolean(KEY_IS_FETCHED_CATEGORIES, false)
  }

  override fun getUUID(): String? = sharedPreferences.getString(KEY_UUID, null)

  override fun getToken(): String? {
    val token = sharedPreferences.getString(KEY_APP_TOKEN, null)
    Timber.d("getToken => $token")
    return token
  }

  companion object {
    const val KEY_IS_FETCHED_CATEGORIES = "key_fetched_categories"

    const val KEY_APP_PREFERENCES = "app_preferences"
    const val KEY_APP_TOKEN = "key_app_token"
    const val KEY_UUID = "key_uuid"
  }
}
