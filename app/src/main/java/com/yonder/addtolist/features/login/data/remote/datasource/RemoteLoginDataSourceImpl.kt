package com.yonder.addtolist.features.login.data.remote.datasource

import com.yonder.addtolist.data.remote.api.ApiService
import com.yonder.addtolist.features.login.data.remote.request.UserRegisterRequest
import javax.inject.Inject

/**
 * Yusuf Onder on 10,May,2021
 */

class RemoteLoginDataSourceImpl @Inject constructor(private val apiService: ApiService) :
  RemoteLoginDataSource {

  override suspend fun register(registerRequest: UserRegisterRequest) =
    apiService.registerGuestUser(registerRequest)

}