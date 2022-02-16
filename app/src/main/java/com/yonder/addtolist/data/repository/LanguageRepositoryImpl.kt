package com.yonder.addtolist.scenes.languageselection.data.repository

import com.yonder.addtolist.core.data.State
import com.yonder.addtolist.core.data.safeApiCall
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.network.ApiService
import com.yonder.addtolist.data.remote.response.LanguageResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */
interface LanguageRepository {
  suspend fun fetchLanguages(): Flow<State<List<LanguageResponse>>>
}

class LanguageRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineThread) : LanguageRepository {
  override suspend fun fetchLanguages(): Flow<State<List<LanguageResponse>>> =
    safeApiCall(dispatcher = dispatcher.io) {
      apiService.getLanguages()
    }
}
