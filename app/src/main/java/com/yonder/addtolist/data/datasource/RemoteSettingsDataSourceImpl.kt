package com.yonder.addtolist.data.datasource

import com.yonder.addtolist.core.network.LoginService
import javax.inject.Inject

/**
 * Yusuf Onder on 12,May,2021
 */

class RemoteSettingsDataSourceImpl @Inject constructor(private val loginService: LoginService) :
    RemoteSettingsDataSource
