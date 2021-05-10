package com.yonder.addtolist.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.common.utils.auth.IAuthUtils
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import com.yonder.addtolist.domain.model.ui.UserUiModel
import com.yonder.addtolist.domain.usecase.LoginUseCase
import com.yonder.addtolist.extensions.toReadableMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase,
  private val authUtils: IAuthUtils
) : ViewModel() {

  private val _state: MutableStateFlow<LoginViewState> =
    MutableStateFlow(LoginViewState.Initial)
  val state: StateFlow<LoginViewState> get() = _state

  fun continueWith(providerType: ProviderType) {
    val loginParams: UserRegisterRequest
    when (providerType) {
      ProviderType.GUEST -> {
        loginParams = authUtils.getGuestUserParams(
          providerType,
          TEST_GCM_TOKEN,
          TEST_DEVICE_UUID
        )
      }
      else -> {
        TODO("Other provider types don't implemented yet")
      }
    }

    loginUseCase.login(loginParams)
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