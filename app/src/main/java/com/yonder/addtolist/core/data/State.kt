package com.yonder.addtolist.core.data

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
sealed class State<out R> {

  data class Success<out T>(val data: T) : State<T>()
  data class Error(val exception: Throwable) : State<Nothing>()
  object Loading: State<Nothing>()

  override fun toString(): String {
    return when (this) {
      is Success<*> -> "Success[data=$data]"
      is Error -> "Error[exception=$exception]"
      Loading -> "Loading"
    }
  }
}

fun <T, R> State<T>.map(transform: (T) -> R): State<R> {
  return when (this) {
    is State.Success -> State.Success(transform(data))
    is State.Error -> State.Error(exception)
    is State.Loading -> State.Loading
  }
}
