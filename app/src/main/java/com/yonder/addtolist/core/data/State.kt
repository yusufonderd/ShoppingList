package com.yonder.addtolist.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
sealed class State<out R> {

  data class Success<out T>(val data: T) : State<T>()
  data class Error(val exception: Throwable) : State<Nothing>()
  object Loading : State<Nothing>()

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

fun <T> Flow<State<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<State<T>> =
  transform { value ->
    if (value is State.Success) {
      action(value.data)
    }
    return@transform emit(value)
  }

fun <T> Flow<State<T>>.doOnError(action: suspend (Throwable) -> Unit): Flow<State<T>> =
  transform { value ->
    if (value is State.Error) {
      action(value.exception)
    }
    return@transform emit(value)
  }

fun <T> Flow<State<T>>.doOnLoading(action: suspend () -> Unit): Flow<State<T>> =
  transform { value ->
    if (value is State.Loading) {
      action()
    }
    return@transform emit(value)
  }

