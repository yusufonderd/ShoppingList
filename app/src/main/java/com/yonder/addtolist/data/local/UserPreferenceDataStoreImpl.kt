package com.yonder.addtolist.data.local

import android.content.SharedPreferences
import com.yonder.addtolist.common.ProviderType
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Yusuf Onder on 07,May,2021
 */

//Default language is English
private const val DEFAULT_LANGUAGE_ID = 2

@Suppress("TooManyFunctions")
class UserPreferenceDataStoreImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
  UserPreferenceDataStore {

  override fun setAppLanguageId(languageId: Int) {
    sharedPreferences.edit().apply {
      putInt(KEY_LANGUAGE, languageId)
      apply()
    }
  }

  override fun getAppLanguageId(): Int {
    return sharedPreferences.getInt(KEY_LANGUAGE, DEFAULT_LANGUAGE_ID)
  }

  override fun setLocale(locale: Locale) {
    sharedPreferences.edit().apply {
      putString(KEY_LOCALE, locale.language)
      apply()
    }
  }

  override fun getLocale(): Locale {
    val localeLanguage = sharedPreferences.getString(KEY_LOCALE, null)
    return if (localeLanguage != null) {
      Locale(localeLanguage)
    } else {
      Locale.ENGLISH
    }
  }

  override fun setCurrency(currency: String) {
    sharedPreferences.edit().apply {
      putString(KEY_CURRENCY, currency)
      apply()
    }
  }

  override fun getCurrency(): String {
    return sharedPreferences.getString(KEY_CURRENCY, "$").orEmpty()
  }

  override fun saveToken(token: String?) {
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

  override fun setFetchedCategoriesAndProducts() {
    sharedPreferences.edit().apply {
      putBoolean(KEY_IS_FETCHED_CATEGORIES, true)
      apply()
    }
  }

  override fun isFetchedCategoriesAndProducts(): Boolean {
    return sharedPreferences.getBoolean(KEY_IS_FETCHED_CATEGORIES, false)
  }

  override fun getUUID(): String? = sharedPreferences.getString(KEY_UUID, null)

  override fun getToken(): String? {
    return sharedPreferences.getString(KEY_APP_TOKEN, null)
  }

  override fun getProviderType(): ProviderType {
    val providerTypeValue = sharedPreferences.getString(
      KEY_PROVIDER_TYPE,
      ProviderType.UNKNOWN.value
    )
    return ProviderType.init(providerTypeValue)
  }


  override fun setProviderType(providerType: ProviderType) {
    sharedPreferences.edit().apply {
      putString(KEY_PROVIDER_TYPE, providerType.value)
      apply()
    }
  }

  companion object {
    const val KEY_IS_FETCHED_CATEGORIES = "key_fetched_categories"
    const val KEY_APP_PREFERENCES = "app_preferences"
    const val KEY_APP_TOKEN = "key_app_token"
    const val KEY_UUID = "key_uuid"
    const val KEY_LANGUAGE = "key_language"
    const val KEY_LOCALE = "key_locale"
    const val KEY_CURRENCY = "key_currency"
    const val KEY_PROVIDER_TYPE = "key_provider_type"

  }
}
