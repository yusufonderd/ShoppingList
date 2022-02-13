package com.yonder.addtolist.domain.decider

import java.lang.Exception

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

@Suppress("TooGenericExceptionCaught","MagicNumber")
object CategoryImageWrapper {

  operator fun invoke(image: String): String {
    if (image.isNotEmpty()) {
      return try {
        val codepoint: Int = image.substring(2).toInt(16)
        val chars = Character.toChars(codepoint)
        String(chars)
      } catch (e: Exception) {
        ""
      }
    }
    return image
  }

}
