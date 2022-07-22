package com.yonder.addtolist.di

import com.yonder.addtolist.core.network.LoginService
import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.core.network.responses.UserResponse
import com.yonder.addtolist.data.repository.LoginRepository
import com.yonder.addtolist.data.repository.LoginRepositoryImpl
import com.yonder.addtolist.domain.mapper.LoginMapper
import com.yonder.addtolist.domain.uimodel.UserUiModel
import com.yonder.core.base.mapper.Mapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

/**
 * Yusuf Onder on 09,May,2021
 */

@[Module InstallIn(ViewModelComponent::class)]
interface LoginModule {

  @get:[Binds]
  val LoginMapper.loginMapper: Mapper<BaseResponse<UserResponse>, UserUiModel>

  @get:[Binds]
  val LoginRepositoryImpl.loginRepository: LoginRepository

  companion object {
    @[Provides]
    fun provideLoginService(retrofit: Retrofit): LoginService {
      return retrofit.create(LoginService::class.java)
    }
  }
}
