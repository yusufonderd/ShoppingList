package com.yonder.addtolist.features.splash.domain

import com.yonder.addtolist.data.local.UserPreferenceDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 20/05/2021
 */
class SplashUseCase @Inject constructor(private val userPreferenceDataStore: UserPreferenceDataStore) {

  fun getUuid() = flow<Any> {
    if (userPreferenceDataStore.uuid != null){
      emit(userPreferenceDataStore.uuid!!)
    }else{
      val randomUuid = UUID.randomUUID().toString()
      userPreferenceDataStore.saveUUID(randomUuid)
      emit(randomUuid)
    }
  }

  fun isLoggedIn(): Flow<Boolean> {
    return flow {
      emit(userPreferenceDataStore.token != null)
    }
  }

}