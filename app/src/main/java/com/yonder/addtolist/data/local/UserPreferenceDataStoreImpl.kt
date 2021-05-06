package com.yonder.addtolist.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Yusuf Onder on 07,May,2021
 */


class UserPreferenceDataStoreImpl @Inject constructor(@ApplicationContext context: Context) :
  UserPreferenceDataStore {
  private val applicationContext = context.applicationContext
  private val dataStore: DataStore<Preferences> = applicationContext.createDataStore(
    name = KEY_APP_PREFERENCES
  )

  override val token: Flow<String?>
    get() = dataStore.data.map { preferences ->
      preferences[KEY_APP_TOKEN]
    }

  override suspend fun saveToken(token: String) {
    dataStore.edit { preferences ->
      preferences[KEY_APP_TOKEN] = token
    }
  }

  companion object {
    const val KEY_APP_PREFERENCES = "app_preferences"
    val KEY_APP_TOKEN = stringPreferencesKey("key_app_token")
  }

}
