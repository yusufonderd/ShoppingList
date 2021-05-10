package com.yonder.addtolist.data.remote.datasource.login

import com.yonder.addtolist.data.remote.ApiService
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import javax.inject.Inject

/**
 * Yusuf Onder on 10,May,2021
 */

class RemoteLoginDataSourceImpl @Inject constructor(private val apiService: ApiService) :
  RemoteLoginDataSource {

  override suspend fun register(registerRequest: UserRegisterRequest) =
    apiService.registerGuestUser(registerRequest)

}