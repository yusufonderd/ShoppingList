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


  fun getLanguageId(): Int {

    when (Locale.getDefault().language) {
      "tr" -> {
        return 1
      }
      "en" -> {
        return 2
      }
      "de" -> {
        return 3
      }
      "fr" -> {
        return 4
      }
      "ru" -> {
        return 5
      }
      "es" -> {
        return 6
      }
      "ar" -> {
        return 7
      }
      "hi" -> {
        return 8
      }
      "ro" -> {
        return 9
      }
      else -> {
        return 2
      }
    }

  }




}