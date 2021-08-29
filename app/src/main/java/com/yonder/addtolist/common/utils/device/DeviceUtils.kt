package com.yonder.addtolist.common.utils.device

import android.content.Context
import android.os.Build
import java.util.*

/**
 * Yusuf Onder on 09,May,2021
 */

object DeviceUtils {

  private const val EMPTY_REGION = ""

  fun getModel(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    return "$model - $manufacturer"
  }

  fun getLocalLanguage(): String = Locale.getDefault().language

  fun getRegion(context: Context): String {
    val configuration = context.resources.configuration
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      configuration.locales.takeIf { it.isEmpty.not() }?.let { locales ->
        locales[0].country
      } ?: run { EMPTY_REGION }
    } else {
      configuration.locale.country
    }
  }

  @Suppress("MagicNumber")
  fun getLanguageId(): Int {

    return when (Locale.getDefault().language) {
      "tr" -> {
        1
      }
      "en" -> {
        2
      }
      "de" -> {
        3
      }
      "fr" -> {
        4
      }
      "ru" -> {
        5
      }
      "es" -> {
        6
      }
      "ar" -> {
        7
      }
      "hi" -> {
        8
      }
      "ro" -> {
        9
      }
      else -> {
        2
      }
    }

  }

}
