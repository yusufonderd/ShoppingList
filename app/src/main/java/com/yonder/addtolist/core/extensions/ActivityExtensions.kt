package com.yonder.addtolist.core.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import java.lang.Exception

/**
 * @author yusuf.onder
 * Created on 10.01.2022
 */


fun Activity?.navigateUrl(url: String) {
    try {
        val browserIntent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        this?.startActivity(browserIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}