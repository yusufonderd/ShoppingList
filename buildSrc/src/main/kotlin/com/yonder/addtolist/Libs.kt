package com.yonder.addtolist

/**
 * Yusuf Onder on 04,May,2021
 */

object Libs {

  object Compose {
    const val runtime = "androidx.compose.runtime:runtime:" + Versions.compose
    const val material = "androidx.compose.material:material:" + Versions.compose
    const val activity = "androidx.activity:activity-compose:" + Versions.compose_activity
    const val animation = "androidx.compose.animation:animation:"+ Versions.compose
    const val coil: String = "io.coil-kt:coil-compose:" + Versions.coil
  }

  object Gradle {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0"
  }

  object Kotlin {
    const val org_jetbrains_kotlin: String =
      "org.jetbrains.kotlin:kotlin-stdlib:" + Versions.jetbrains_kotlin
    const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:" + Versions.jetbrains_kotlin
  }

  object Room{
    const val room_ktx = "androidx.room:room-ktx:" + Versions.room
    const val room_compiler = "androidx.room:room-compiler:" + Versions.room
  }



  object GoogleServices {
    const val service_plugin = "com.google.gms:google-services:" + Versions.google_service_auth
    const val auth = "com.google.android.gms:play-services-auth:" + Versions.google_auth
  }

  object Firebase{
    const val bom = "com.google.firebase:firebase-bom:" + Versions.firebase_bom
    const val messaging = "com.google.firebase:firebase-messaging-ktx"
    const val analytics = "com.google.firebase:firebase-analytics-ktx"
  }

  object Log{
    const val timber = "com.jakewharton.timber:timber:" + Versions.timber
  }
  object Network {
    const val gson: String = "com.google.code.gson:gson:" + Versions.gson
    const val retrofit = "com.squareup.retrofit2:retrofit:" + Versions.retrofit
    const val converter = "com.squareup.retrofit2:converter-gson:" + Versions.retrofit
    const val okhttp = "com.squareup.okhttp3:okhttp:" + Versions.ok_http
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:" + Versions.ok_http
  }

  object GooglePlay{
    const val core = "com.google.android.play:core:1.8.0"
    const val coreKtx = "com.google.android.play:core-ktx:1.8.1"
  }

  object AndroidX {
    const val androidx_appcompat: String =
      "androidx.appcompat:appcompat:" + Versions.androidx_appcompat
    const val core_ktx: String = "androidx.core:core-ktx:" + Versions.core_ktx
    const val constraint_layout: String =
      "androidx.constraintlayout:constraintlayout:" + Versions.constraint_layout
    const val navigation_fragment: String =
      "androidx.navigation:navigation-fragment-ktx:" + Versions.navigation

    const val nav_args_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:" + Versions.navigation
    const val navigation_ui_ktx: String =
      "androidx.navigation:navigation-ui-ktx:" + Versions.navigation
    const val legacy_support: String =
      "androidx.legacy:legacy-support-v4:" + Versions.legacy_support
    const val live_data_vm: String =
      "androidx.lifecycle:lifecycle-livedata-ktx:" + Versions.livedata
    const val live_data_ktx: String =
      "androidx.lifecycle:lifecycle-viewmodel-ktx:" + Versions.livedata
    const val dataStore: String = "androidx.datastore:datastore-preferences:" + Versions.dataStore

    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:" + Versions.lifecycle
    const val common = "androidx.lifecycle:lifecycle-common-java8:" + Versions.lifecycle
  }


  object DaggerHilt {
    const val daggerHilt = "com.google.dagger:hilt-android:" + Versions.hilt_version
    const val daggerHiltCompiler = "com.google.dagger:hilt-compiler:" + Versions.hilt_version
    const val daggerHiltGradlePlugin =
      "com.google.dagger:hilt-android-gradle-plugin:" + Versions.hilt_version
  }



}