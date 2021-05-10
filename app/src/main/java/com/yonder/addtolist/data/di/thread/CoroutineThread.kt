package com.yonder.addtolist.data.di.thread

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Yusuf Onder on 06,May,2021
 */

interface CoroutineThread {
  val default: CoroutineDispatcher
  val main: CoroutineDispatcher
  val io: CoroutineDispatcher
}