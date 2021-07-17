package com.yonder.addtolist.features.login.di

import android.content.Context
import com.facebook.CallbackManager
import com.yonder.addtolist.common.utils.auth.FacebookUserProvider
import com.yonder.addtolist.common.utils.auth.GoogleUserProvider
import com.yonder.addtolist.common.utils.auth.GuestUserProvider
import com.yonder.addtolist.common.utils.auth.UserProvider
import com.yonder.addtolist.core.base.BaseDecider
import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.core.network.responses.BaseResponse
import com.yonder.addtolist.features.login.data.remote.LoginService
import com.yonder.addtolist.features.login.data.remote.datasource.RemoteLoginDataSource
import com.yonder.addtolist.features.login.data.remote.datasource.RemoteLoginDataSourceImpl
import com.yonder.addtolist.features.login.domain.decider.LoginDecider
import com.yonder.addtolist.features.login.domain.mapper.LoginMapper
import com.yonder.addtolist.features.login.data.remote.response.UserResponse
import com.yonder.addtolist.features.login.domain.model.UserUiModel
import com.yonder.addtolist.features.login.domain.repository.LoginRepository
import com.yonder.addtolist.features.login.domain.repository.LoginRepositoryImpl
import com.yonder.addtolist.features.login.domain.usecase.FacebookGraphUseCase
import com.yonder.addtolist.features.login.domain.usecase.FacebookGraphUseCaseImpl
import com.yonder.addtolist.features.login.domain.usecase.LoginUseCase
import com.yonder.addtolist.features.login.domain.usecase.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit

/**
 * Yusuf Onder on 09,May,2021
 */


@[Module InstallIn(ViewModelComponent::class)]
interface LoginModule {

  @get:[Binds]
  val LoginMapper.loginMapper: Mapper<BaseResponse<UserResponse>, UserUiModel>

  @get:[Binds]
  val LoginUseCaseImpl.loginUseCase: LoginUseCase

  @get:[Binds]
  val FacebookGraphUseCaseImpl.facebookUseCase: FacebookGraphUseCase

  @get:[Binds]
  val RemoteLoginDataSourceImpl.remoteLoginDataSource: RemoteLoginDataSource

  @get:[Binds]
  val LoginRepositoryImpl.loginRepository: LoginRepository

  @get:[Binds]
  val LoginDecider.loginDecider: BaseDecider<UserResponse>

  companion object {
    @[Provides]
    fun provideFacebookUserProvider(@ApplicationContext context: Context): UserProvider {
      return FacebookUserProvider(context)
    }

    @[Provides]
    fun provideGoogleUserProvider(@ApplicationContext context: Context): UserProvider {
      return GoogleUserProvider(context)
    }

    @[Provides]
    fun provideGuestUserProvider(@ApplicationContext context: Context): UserProvider {
      return GuestUserProvider(context)
    }

    @[Provides]
    fun provideFacebookCallbackManager(): CallbackManager {
      return CallbackManager.Factory.create()
    }

    @[Provides]
    fun provideLoginService(retrofit: Retrofit): LoginService {
      return retrofit.create(LoginService::class.java)
    }
  }
}
