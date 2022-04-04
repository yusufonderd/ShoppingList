package com.yonder.addtolist.common.ui.extensions

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */

fun Context.showToastMessage(@StringRes messageResId: Int) {
    Toast.makeText(this, getString(messageResId), Toast.LENGTH_LONG).show()
}

fun Context.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context?.navigate(url: String) {
    this?.startActivity(
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
    )
}

fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

