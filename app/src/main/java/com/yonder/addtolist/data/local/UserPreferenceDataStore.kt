package com.yonder.addtolist.data.local

import java.util.*

/**
 * Yusuf Onder on 07,May,2021
 */

@Suppress("TooManyFunctions")
interface UserPreferenceDataStore {
    fun getAppLanguageId(): Int
    fun setAppLanguageId(languageId: Int)
    fun getLocale(): Locale
    fun setLocale(locale: Locale)
    fun getCurrency(): String
    fun setCurrency(currency: String)
    fun getUUID(): String?
    fun getToken(): String?
    fun getFullName(): String?
    fun getProfileUrl(): String?
    fun setProfileUrl(url: String)
    fun setFullName(name: String)
    fun saveUUID(uuid: String)
    fun saveToken(token: String?)
    fun isFetchedCategoriesAndProducts(): Boolean
    fun setFetchedCategoriesAndProducts()
    fun getSelectedListUUID(): String?
    fun setSelectedListUUID(uuid: String)
}
