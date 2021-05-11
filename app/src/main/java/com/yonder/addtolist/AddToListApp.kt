package com.yonder.addtolist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Yusuf Onder on 06,May,2021
 */

@HiltAndroidApp
class AddToListApp : Application() {

  override fun onCreate() {
    super.onCreate()
    setupTimber()
  }

  private fun setupTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}