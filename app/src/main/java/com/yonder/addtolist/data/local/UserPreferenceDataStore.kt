package com.yonder.addtolist.data.local

import kotlinx.coroutines.flow.Flow


/**
 * Yusuf Onder on 07,May,2021
 */

interface UserPreferenceDataStore {
  val token: Flow<String?>
  suspend fun saveToken(token: String)
}