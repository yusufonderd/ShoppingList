package com.yonder.addtolist

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Yusuf Onder on 06,May,2021
 */

@HiltAndroidApp
class AddToListApp : Application() {

  override fun onCreate() {
    super.onCreate()
    setupFirebase()
    setupTimber()
  }

  private fun setupFirebase() {
    FirebaseApp.initializeApp(this)
  }

  private fun setupTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}