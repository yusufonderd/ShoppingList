package com.yonder.addtolist.data.local

import android.content.SharedPreferences
import com.yonder.addtolist.common.enums.ProviderType
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


    override fun setFullName(name: String) {
        sharedPreferences.edit().apply {
            putString(KEY_FULL_NAME, name)
            apply()
        }
    }

    override fun getFullName(): String? {
        return sharedPreferences.getString(KEY_FULL_NAME, null)

    }
    override fun setProfileUrl(url: String) {
        sharedPreferences.edit().apply {
            putString(KEY_PROFILE_URL, url)
            apply()
        }
    }

    override fun getProfileUrl(): String? {
        return sharedPreferences.getString(KEY_PROFILE_URL, null)
    }

    override fun setLocale(locale: Locale) {
        sharedPreferences.edit().apply {
            putString(KEY_LOCALE, locale.language)
            apply()
        }
    }

    override fun getLocale(): Locale {
        val localeLanguage = sharedPreferences.getString(KEY_LOCALE, null)
        return if (localeLanguage == null) {
            Locale.ENGLISH
        } else {
            Locale(localeLanguage)
        }
    }

    override fun getSelectedListUUID(): String? {
        return sharedPreferences.getString(KEY_SELECTED_LIST,null)
    }

    override fun setSelectedListUUID(uuid: String) {
        sharedPreferences.edit().apply {
            putString(KEY_SELECTED_LIST, uuid)
            apply()
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

    companion object {
        const val KEY_IS_FETCHED_CATEGORIES = "key_fetched_categories"
        const val KEY_APP_PREFERENCES = "app_preferences"
        const val KEY_APP_TOKEN = "key_app_token"
        const val KEY_UUID = "key_uuid"
        const val KEY_LANGUAGE = "key_language"
        const val KEY_LOCALE = "key_locale"
        const val KEY_CURRENCY = "key_currency"
        const val KEY_PROFILE_URL = "key_profile_url"
        const val KEY_FULL_NAME = "key_full_name"
        const val KEY_SELECTED_LIST = "key_selected_list"

    }
}
