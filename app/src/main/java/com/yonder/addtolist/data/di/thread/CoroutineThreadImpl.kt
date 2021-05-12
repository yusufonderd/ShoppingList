package com.yonder.addtolist.data.di.thread

/**
 * Yusuf Onder on 06,May,2021
 */
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CoroutineThreadImpl @Inject constructor() : CoroutineThread {
  override val default: CoroutineDispatcher = Dispatchers.Default
  override val main: CoroutineDispatcher = Dispatchers.Main
  override val io: CoroutineDispatcher = Dispatchers.IO
}

