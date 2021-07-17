package com.yonder.addtolist.core.di.modules

import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.data.remote.api.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Yusuf Onder on 06,May,2021
 */


@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

  val provideLoggingInterceptor: HttpLoggingInterceptor
    @[Provides Singleton] get() = HttpLoggingInterceptor().also { interceptor ->
      interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

  @[Provides Singleton]
  fun provideAuthInterceptor(userPreferenceDataStore: UserPreferenceDataStore): AuthInterceptor {
    return AuthInterceptor(userPreferenceDataStore)
  }

  @[Provides Singleton]
  fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    authInterceptor: AuthInterceptor
  ): OkHttpClient =
    OkHttpClient.Builder().apply {
      addInterceptor(authInterceptor)
      addInterceptor(loggingInterceptor)
    }.build()

  @[Provides Singleton]
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

}

