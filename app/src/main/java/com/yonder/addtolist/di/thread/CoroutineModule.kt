package com.yonder.addtolist.di.thread

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Yusuf Onder on 06,May,2021
 */

@InstallIn(SingletonComponent::class)
@Module
interface CoroutineModule {

  @get:[Binds Singleton]
  val CoroutineThreadImpl.coroutineThread: CoroutineThread
}