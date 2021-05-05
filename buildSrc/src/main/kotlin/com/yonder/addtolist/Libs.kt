package com.yonder.addtolist

/**
 * Yusuf Onder on 04,May,2021
 */

object Libs {

  object Kotlin {
    const val org_jetbrains_kotlin: String =
      "org.jetbrains.kotlin:kotlin-stdlib:" + Versions.jetbrains_kotlin
  }

  object UnitTest {
    const val junit_test: String = "androidx.test.ext:junit:" + Versions.junit_test
    const val junit: String = "junit:junit:" + Versions.junit
  }

  object AndroidTest {
    const val espresso_core: String = "androidx.test.espresso:espresso-core:" +
        Versions.espresso_core
  }

  object Network {
    const val gson: String = "com.google.code.gson:gson:" + Versions.gson
  }

  object AndroidX {
    const val androidx_appcompat: String =
      "androidx.appcompat:appcompat:" + Versions.androidx_appcompat
    const val core_ktx: String = "androidx.core:core-ktx:" + Versions.core_ktx
    const val constraint_layout: String =
      "androidx.constraintlayout:constraintlayout:" + Versions.constraint_layout
  }

  object Design {
    const val material: String = "com.google.android.material:material:" + Versions.material
  }


}