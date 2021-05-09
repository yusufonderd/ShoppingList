package com.yonder.addtolist.di.network

import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Yusuf Onder on 06,May,2021
 */


@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

  val provideLoggingInterceptor: HttpLoggingInterceptor
    @[Provides] get() = HttpLoggingInterceptor().also { interceptor ->
      interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

  @[Provides]
  fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder().apply {
      addInterceptor(loggingInterceptor)
    }.build()


  @[Provides]
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @[Provides]
  fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

}

