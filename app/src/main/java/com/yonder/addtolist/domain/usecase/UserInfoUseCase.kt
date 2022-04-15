package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.data.local.UserPreferenceDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 20/05/2021
 */
class UserInfoUseCase @Inject constructor(private val userPreferenceDataStore: UserPreferenceDataStore) {

    fun getUuid() {
        val currentUUID = userPreferenceDataStore.getUUID()
        if (currentUUID == null) {
            val newUUID: String = UUID.randomUUID().toString()
            userPreferenceDataStore.saveUUID(newUUID)
        }
    }

    fun isLoggedIn(): Boolean = userPreferenceDataStore.getToken() != null

    fun removeToken() = userPreferenceDataStore.saveToken(null)


}
