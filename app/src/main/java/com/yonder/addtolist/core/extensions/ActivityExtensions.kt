package com.yonder.addtolist.core.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.google.android.play.core.review.ReviewManagerFactory
import timber.log.Timber
import java.lang.Exception

/**
 * @author yusuf.onder
 * Created on 10.01.2022
 */
fun Activity?.reviewApp() = this?.run {
    val manager = ReviewManagerFactory.create(this)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener { result ->
        if (result.isSuccessful) {
            val reviewInfo = request.result
            val flow = manager.launchReviewFlow(this, reviewInfo)
            flow.addOnCompleteListener { task -> }
        }
    }
}

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