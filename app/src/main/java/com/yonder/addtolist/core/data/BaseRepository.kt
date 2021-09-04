package com.yonder.addtolist.core.data

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
import com.yonder.addtolist.core.network.responses.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCall(
  dispatcher: CoroutineDispatcher = Dispatchers.IO,
  call: suspend () -> Response<BaseResponse<T>>,
): Flow<State<T>> {
  return flow {
    emit(State.Loading)
    val response = call.invoke()
    val responseData = response.body()?.data
    if (response.isSuccessful && responseData != null) {
      emit(State.Success(data = responseData))
    } else {
      val errorBody = response.errorBody()
      if (errorBody != null) {
        State.Error(IOException(errorBody.toString()))
      } else {
        State.Error(IOException("Unknown error"))
      }
    }
  }.catch { error ->
    error.printStackTrace()
    emit(State.Error(error))
  }.flowOn(dispatcher)

}




