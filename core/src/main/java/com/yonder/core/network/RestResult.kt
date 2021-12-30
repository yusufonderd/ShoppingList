package com.yonder.core.network

/**
 * Yusuf Onder on 09,May,2021
 */

sealed class RestResult<out T> {

  data class Success<T>(val data: T) : RestResult<T>()

  data class Error<T>(val throwable: Throwable) : RestResult<T>()

  object Loading : RestResult<Nothing>()

  fun onSuccess(handler: (T) -> Unit): RestResult<T> = this.also {
    if (this is Success) handler(data)
  }

  fun onLoading(handler: () -> Unit): RestResult<T> = this.also {
    if (this is Loading) handler()
  }

  fun onError(handler: (t: Throwable) -> Unit): RestResult<T> = this.also {
    if (this is Error) handler(throwable)
  }

  fun onSuccessOrError(handler: () -> Unit): RestResult<T> = this.also {
    if (this is Success || this is Error) handler()
  }

}

