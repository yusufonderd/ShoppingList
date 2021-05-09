package com.yonder.addtolist.core

/**
 * Yusuf Onder on 09,May,2021
 */

sealed class NetworkResult<out T> {

  data class Success<T>(val data: T) : NetworkResult<T>()

  data class Error<T>(val throwable: Throwable) : NetworkResult<T>()

  object Loading : NetworkResult<Nothing>()

  fun onSuccess(handler: (T) -> Unit): NetworkResult<T> = this.also {
    if (this is Success) handler(data)
  }

  fun onError(handler: (t: Throwable) -> Unit): NetworkResult<T> = this.also {
    if (this is Error) handler(throwable)
  }

}