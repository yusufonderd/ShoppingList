package com.yonder.addtolist.common.utils.device

/**
 * Yusuf Onder on 09,May,2021
 */

object LanguageUtils {

  fun getLanguageIdBy(language: String) = LANGUAGES.find { it.first == language }?.second ?: EN.second

  private val TR = "tr" to 1
  private val EN = "en" to 2
  private val DE = "de" to 3
  private val FR = "fr" to 4
  private val RU = "ru" to 5
  private val ES = "es" to 6
  private val AR = "ar" to 7
  private val IN = "hi" to 8
  private val RO = "ro" to 9

  private val LANGUAGES = arrayListOf(TR, EN, DE, FR, RU, ES, AR, IN, RO)
}