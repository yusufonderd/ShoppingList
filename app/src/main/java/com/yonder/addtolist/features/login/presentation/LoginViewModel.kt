package com.yonder.addtolist.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.messaging.FirebaseMessaging
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.common.utils.auth.FacebookUserProvider
import com.yonder.addtolist.common.utils.auth.GoogleUserProvider
import com.yonder.addtolist.common.utils.auth.GuestUserProvider
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.features.login.data.remote.request.UserRegisterRequest
import com.yonder.addtolist.features.login.domain.model.UserUiModel
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.features.login.domain.usecase.FacebookGraphUseCase
import com.yonder.addtolist.features.login.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase,
  private val facebookUserProvider: FacebookUserProvider,
  private val googleUserProvider: GoogleUserProvider,
  private val guestUserProvider: GuestUserProvider,
  private val userPreferenceDataStore: UserPreferenceDataStore,
  internal val callbackManager: CallbackManager,
  private val facebookGraphExecute: FacebookGraphUseCase
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
    createNewUser(googleUserProvider.create(account))
  }

  fun continueWithFacebook(loginResult: LoginResult) {
    facebookGraphExecute.getUserInfo(loginResult) { userInfoObject ->
      createNewUser(facebookUserProvider.create(userInfoObject))
    }
  }

  fun continueAsGuest() {
    createNewUser(guestUserProvider.create())
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
      createUserRegisterRequest.fcmToken = token.orEmpty()
      createUserRegisterRequest.deviceUUID = userPreferenceDataStore.uuid.orEmpty()
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

  private fun onLoginError(error: Throwable) {
    _state.value = LoginViewState.Error(error.toReadableMessage())
  }

  private fun onLoginSuccess(userUiModel: UserUiModel) {
    userUiModel.token?.let { token ->
      userPreferenceDataStore.saveToken(token)
      _state.value = LoginViewState.NavigateLogin
    } ?: run {
      _state.value = LoginViewState.Error(userUiModel.result.message)
    }
  }

}

sealed class LoginViewState {
  object Initial : LoginViewState()
  object NavigateLogin : LoginViewState()
  data class Error(val message: String) : LoginViewState()
}