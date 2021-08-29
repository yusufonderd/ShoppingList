package com.yonder.addtolist.scenes.productdetail.model.wrapper

import java.lang.Exception

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

object CategoryImageWrapper {

  fun wrap(image: String): String {
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