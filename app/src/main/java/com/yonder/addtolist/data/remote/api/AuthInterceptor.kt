package com.yonder.addtolist.data.remote.api

import com.yonder.addtolist.data.local.UserPreferenceDataStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class AuthInterceptor @Inject constructor(
  private val userPreferenceDataStore: UserPreferenceDataStore,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = chain.request().newBuilder()
    userPreferenceDataStore.token?.let { token ->
      builder.addHeader(AUTHORIZATION, "$BEARER $token")
    }
    val request = builder.build()
    return chain.proceed(request)
  }

  companion object {
    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer"
  }
}
