package com.yonder.addtolist.di.login

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.core.base.BaseDecider
import com.yonder.addtolist.core.base.BaseMapper
import com.yonder.addtolist.core.BaseResponse
import com.yonder.addtolist.domain.decider.LoginDecider
import com.yonder.addtolist.domain.mapper.LoginMapper
import com.yonder.addtolist.domain.model.UserResponse
import com.yonder.addtolist.domain.repository.LoginRepository
import com.yonder.addtolist.domain.repository.LoginRepositoryImpl
import com.yonder.addtolist.domain.uimodel.UserUiModel
import com.yonder.addtolist.domain.usecase.LoginUseCase
import com.yonder.addtolist.domain.usecase.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

/**
 * Yusuf Onder on 09,May,2021
 */


@[Module InstallIn(ViewModelComponent::class)]
interface LoginModule {

  @get:[Binds ]
  val LoginMapper.loginMapper: BaseMapper<BaseResponse<UserResponse>, UserUiModel>

  @get:[Binds ]
  val LoginUseCaseImpl.loginUseCase: LoginUseCase


  @get:[Binds ]
  val LoginRepositoryImpl.loginRepository: LoginRepository

  @get:[Binds]
  val LoginDecider.loginDecider: BaseDecider


 /* companion object{

    @[Provides]
    fun provideUseCase(repository: LoginRepository,loginMapper: LoginMapper): LoginUseCaseImpl =
      LoginUseCaseImpl(repository,loginMapper)

  }*/
}
