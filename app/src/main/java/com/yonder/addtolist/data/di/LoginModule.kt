package com.yonder.addtolist.data.di

import android.content.Context
import com.yonder.addtolist.common.utils.auth.NewUserProviderImpl
import com.yonder.addtolist.common.utils.auth.NewUserProvider
import com.yonder.addtolist.core.base.BaseDecider
import com.yonder.addtolist.core.base.BaseMapper
import com.yonder.addtolist.core.base.BaseResponse
import com.yonder.addtolist.data.remote.datasource.login.RemoteLoginDataSource
import com.yonder.addtolist.data.remote.datasource.login.RemoteLoginDataSourceImpl
import com.yonder.addtolist.domain.decider.LoginDecider
import com.yonder.addtolist.domain.mapper.LoginMapper
import com.yonder.addtolist.domain.model.response.UserResponse
import com.yonder.addtolist.domain.repository.LoginRepository
import com.yonder.addtolist.domain.repository.LoginRepositoryImpl
import com.yonder.addtolist.domain.model.ui.UserUiModel
import com.yonder.addtolist.domain.usecase.LoginUseCase
import com.yonder.addtolist.domain.usecase.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Yusuf Onder on 09,May,2021
 */


@[Module InstallIn(ViewModelComponent::class)]
interface LoginModule {

  @get:[Binds]
  val LoginMapper.loginMapper: BaseMapper<BaseResponse<UserResponse>, UserUiModel>

  @get:[Binds]
  val LoginUseCaseImpl.loginUseCase: LoginUseCase

  @get:[Binds]
  val RemoteLoginDataSourceImpl.remoteLoginDataSource: RemoteLoginDataSource

  @get:[Binds]
  val LoginRepositoryImpl.loginRepository: LoginRepository

  @get:[Binds]
  val LoginDecider.loginDecider: BaseDecider<UserResponse>


  companion object{
    @[Provides]
    fun provideAuthUtils(@ApplicationContext context : Context) : NewUserProvider{
     return NewUserProviderImpl(context)
    }
  }
}
