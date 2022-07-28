package com.yonder.addtolist.common.utils.device

/**
 * Yusuf Onder on 09,May,2021
 */
@Suppress("MagicNumber")
object LanguageUtils {

    fun getLanguageIdBy(language: String) =
        LANGUAGES.find { it.first == language }?.second ?: EN.second

    val TR = "tr" to 1
    val EN = "en" to 2
    val DE = "de" to 3
    val FR = "fr" to 4
    val RU = "ru" to 5
    val ES = "es" to 6
    val AR = "ar" to 7
    val IN = "hi" to 8
    private val RO = "ro" to 9

    val supportedLanguages = listOf(TR.first, EN.first, DE.first, FR.first)

    private val LANGUAGES = arrayListOf(TR, EN, DE, FR, RU, ES, AR, IN, RO)
}
