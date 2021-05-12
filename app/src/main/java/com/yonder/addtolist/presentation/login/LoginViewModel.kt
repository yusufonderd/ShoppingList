package com.yonder.addtolist.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.messaging.FirebaseMessaging
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.common.utils.auth.NewUserProvider
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.domain.model.request.UserRegisterRequest
import com.yonder.addtolist.domain.model.ui.UserUiModel
import com.yonder.addtolist.domain.usecase.LoginUseCase
import com.yonder.addtolist.extensions.toReadableMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase,
  private val newUserProvider: NewUserProvider,
  private val userPreferenceDataStore: UserPreferenceDataStore,
  internal val callbackManager: CallbackManager,
  private val facebookGraphExecute: FacebookGraphExecute
) : ViewModel() {

  private val _state: MutableStateFlow<LoginViewState> =
    MutableStateFlow(LoginViewState.Initial)
  val state: StateFlow<LoginViewState> get() = _state

  internal val facebookCallback = object : FacebookCallback<LoginResult> {
    override fun onSuccess(result: LoginResult?) {
      result?.let(::continueWithFacebook)
    }

    override fun onCancel() = Unit
    override fun onError(error: FacebookException?) {
      _state.value = LoginViewState.Error(error.toReadableMessage())
    }
  }

  fun continueWithGoogle(account: GoogleSignInAccount) {
    val loginParams = newUserProvider.createUserRegisterRequest(
      ProviderType.GOOGLE,
      account
    )
    createNewUser(loginParams)
  }

  fun continueWithFacebook(loginResult: LoginResult) {
    facebookGraphExecute.getUserInfo(loginResult) { userInfoObject ->
      val loginParams = newUserProvider.createUserRegisterRequest(
        ProviderType.FACEBOOK,
        userInfoObject
      )
      createNewUser(loginParams)
    }
  }

  fun continueAsGuest() {
    val newUserRegisterRequest = newUserProvider.createUserRegisterRequest(ProviderType.GUEST)
    createNewUser(newUserRegisterRequest)
  }

  private fun getDeviceUuid(invoker: (uuid: String) -> Unit) {
    userPreferenceDataStore.uuid.onEach { uuid ->
      invoker.invoke(uuid.orEmpty())
    }.launchIn(viewModelScope)
  }

  private fun getFirebaseToken(invoker: (fcmToken: String?) -> Unit) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
      if (task.isSuccessful) {
        invoker.invoke(task.result)
      } else {
        invoker.invoke(null)
      }
    }
  }

  private fun createNewUser(createUserRegisterRequest: UserRegisterRequest) {
    getFirebaseToken { token ->
      getDeviceUuid { uuid ->
        createUserRegisterRequest.fcmToken = token.orEmpty()
        createUserRegisterRequest.deviceUUID = uuid
        loginUseCase.login(createUserRegisterRequest)
          .onEach { result ->
            result.onSuccess { userUiModel ->
              onLoginSuccess(userUiModel)
            }.onError { error ->
              onLoginError(error)
            }
          }.launchIn(viewModelScope)
      }
    }
  }

  private fun onLoginError(error: Throwable) {
    _state.value = LoginViewState.Error(error.toReadableMessage())
  }

  private fun onLoginSuccess(userUiModel: UserUiModel) {
    viewModelScope.launch {
      userPreferenceDataStore.saveToken(userUiModel.token)
    }
    _state.value = LoginViewState.NavigateLogin
  }

}

sealed class LoginViewState {
  object Initial : LoginViewState()
  object NavigateLogin : LoginViewState()
  data class Error(val message: String) : LoginViewState()
}