package com.yonder.addtolist.core.data

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@Suppress("UnnecessaryAbstractClass")
abstract class BaseRepository {
  fun <T : Any> apiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, call: suspend () -> T): Flow<State<T>> =
    flow {
      emit(State.Loading)
      emit(State.Success(data = call.invoke()))
    }.catch { error ->
      error.printStackTrace()
      emit(State.Error(error))
    }.flowOn(dispatcher)
}




