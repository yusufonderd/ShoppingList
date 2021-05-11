package com.yonder.addtolist.presentation.login

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.common.utils.auth.NewUserProvider
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import com.yonder.addtolist.domain.model.ui.UserUiModel
import com.yonder.addtolist.domain.usecase.LoginUseCase
import com.yonder.addtolist.extensions.toReadableMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase,
  private val newUserProvider: NewUserProvider
) : ViewModel() {

  private val _state: MutableStateFlow<LoginViewState> =
    MutableStateFlow(LoginViewState.Initial)
  val state: StateFlow<LoginViewState> get() = _state

  var callbackManager: CallbackManager = CallbackManager.Factory.create()
  internal val facebookCallback = object : FacebookCallback<LoginResult> {
    override fun onSuccess(result: LoginResult?) {
      result?.let(::continueWithFacebook)
    }

    override fun onCancel() = Unit
    override fun onError(error: FacebookException?) {
      _state.value = LoginViewState.Error(error.toReadableMessage())
    }
  }

  fun continueWithGoogle(account: GoogleSignInAccount?) {
    account?.let {
      val loginParams = newUserProvider.createUserRegisterRequest(
        ProviderType.FACEBOOK,
        TEST_GCM_TOKEN,
        TEST_DEVICE_UUID,
        account
      )
      createNewUser(loginParams)
    }
  }

  fun continueWithFacebook(loginResult: LoginResult) {
    val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, _ ->
      val loginParams = newUserProvider.createUserRegisterRequest(
        ProviderType.FACEBOOK,
        TEST_GCM_TOKEN,
        TEST_DEVICE_UUID,
        `object`
      )
      createNewUser(loginParams)
    }
    val parameters = Bundle()
    parameters.putString("fields", "id,first_name,last_name,email")
    request.parameters = parameters
    request.executeAsync()
  }

  fun continueAsGuest() {
    val newUserRegisterRequest = newUserProvider.createUserRegisterRequest(
      ProviderType.GUEST,
      TEST_GCM_TOKEN,
      TEST_DEVICE_UUID
    )
    createNewUser(newUserRegisterRequest)
  }


  private fun createNewUser(createUserRegisterRequest: UserRegisterRequest) {
    loginUseCase.login(createUserRegisterRequest)
      .onEach { result ->
        result.onSuccess { userUiModel ->
          onLoginSuccess(userUiModel)
        }.onError { error ->
          onLoginError(error)
        }
      }
      .launchIn(viewModelScope)
  }


  private fun onLoginError(error: Throwable) {
    _state.value = LoginViewState.Error(error.toReadableMessage())
  }

  private fun onLoginSuccess(userUiModel: UserUiModel) {
    TODO("make what you need with model object $userUiModel")
  }

  companion object {
    const val TEST_GCM_TOKEN = "TEST_GCM_TOKEN"
    val TEST_DEVICE_UUID = UUID.randomUUID().toString()
  }
}

sealed class LoginViewState {
  object Initial : LoginViewState()
  data class Error(val message: String) : LoginViewState()
}