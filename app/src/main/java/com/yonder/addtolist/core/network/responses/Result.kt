package com.yonder.addtolist.core.network.responses

/**
 * Yusuf Onder on 09,May,2021
 */

sealed class Result<out T> {

  data class Success<T>(val data: T) : Result<T>()

  data class Error<T>(val throwable: Throwable) : Result<T>()

  object Loading : Result<Nothing>()

  fun onSuccess(handler: (T) -> Unit): Result<T> = this.also {
    if (this is Success) handler(data)
  }

  fun onLoading(handler: () -> Unit): Result<T> = this.also {
    if (this is Loading) handler()
  }

  fun onError(handler: (t: Throwable) -> Unit): Result<T> = this.also {
    if (this is Error) handler(throwable)
  }

}