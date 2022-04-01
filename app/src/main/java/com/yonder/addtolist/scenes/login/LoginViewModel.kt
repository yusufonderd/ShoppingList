package com.yonder.addtolist.scenes.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.messaging.FirebaseMessaging
import com.yonder.addtolist.common.utils.auth.FacebookUserProvider
import com.yonder.addtolist.common.utils.auth.GoogleUserProvider
import com.yonder.addtolist.common.utils.auth.GuestUserProvider
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.core.network.UserRegisterRequest
import com.yonder.addtolist.domain.uimodel.UserUiModel
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.domain.usecase.FacebookGraphUseCase
import com.yonder.addtolist.domain.usecase.LoginUseCase
import com.yonder.core.base.BaseViewModel
import com.yonder.core.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@Suppress("LongParameterList")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val facebookUserProvider: FacebookUserProvider,
    private val googleUserProvider: GoogleUserProvider,
    private val guestUserProvider: GuestUserProvider,
    private val userPreferenceDataStore: UserPreferenceDataStore,
    internal val callbackManager: CallbackManager,
    private val facebookGraphExecute: FacebookGraphUseCase
) : BaseViewModel<LoginViewState>() {

    private val _state = MutableLiveData<LoginViewState>()
    val state: LiveData<LoginViewState> = _state

    internal val facebookCallback = object : FacebookCallback<LoginResult> {
        /* override fun onSuccess(result: LoginResult?) {
          result?.let(::continueWithFacebook)
        }

        override fun onCancel() = Unit
        override fun onError(error: FacebookException?) {
          _state.value = LoginViewState.Error(error.toReadableMessage())
        }*/
        override fun onCancel() {

        }

        override fun onError(error: FacebookException) {

        }

        override fun onSuccess(result: LoginResult) {

        }
    }

    fun continueWithGoogle(account: GoogleSignInAccount) {
        createNewUser(googleUserProvider.create(account))
    }

    fun continueWithFacebook(loginResult: LoginResult) {
        facebookGraphExecute.invoke(loginResult) { userInfoObject ->
            createNewUser(facebookUserProvider.create(userInfoObject))
        }
    }

    fun continueAsGuest() {
        createNewUser(guestUserProvider.create())
    }


    private inline fun getFirebaseToken(crossinline invoker: (fcmToken: String?) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                invoker.invoke(task.result)
            } else {
                invoker.invoke(null)
            }
        }
    }

    private fun createNewUser(createUserRegisterRequest: UserRegisterRequest) {
        getFirebaseToken { token: String? ->
            createUserRegisterRequest.fcmToken = token.orEmpty()
            createUserRegisterRequest.deviceUUID = userPreferenceDataStore.getUUID().orEmpty()
            loginUseCase(createUserRegisterRequest)
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
        pushEvent(LoginViewState.Error(error.toReadableMessage()))
    }

    private fun onLoginSuccess(userUiModel: UserUiModel) {
        val token = userUiModel.token
        if (token != null) {
            userPreferenceDataStore.saveToken(token)
            userPreferenceDataStore.setFullName(userUiModel.fullName)
            userPreferenceDataStore.setProfileUrl(userUiModel.profileImage)
            userPreferenceDataStore.setProviderType(userUiModel.providerType)
            pushEvent(LoginViewState.NavigateToShoppingItems)
        } else {
            pushEvent(LoginViewState.Error(userUiModel.result.message))
        }
    }


    private val profileTracker =
        object : ProfileTracker() {
            override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile?) {
                if (currentProfile != null) {
                    this@LoginViewModel.updateProfile(currentProfile)
                } else {
                    this@LoginViewModel.resetProfile()
                }
            }
        }

    private val _profileViewState = MutableLiveData(ProfileViewState(Profile.getCurrentProfile()))
    val profileViewState: LiveData<ProfileViewState> = _profileViewState

    private fun updateProfile(profile: Profile) {
        _profileViewState.value = _profileViewState.value?.copy(profile = profile)
    }

    private fun resetProfile() {
        _profileViewState.value = _profileViewState.value?.copy(profile = null)
    }

}

sealed class LoginViewState : Event {
    object Initial : LoginViewState()
    object NavigateToShoppingItems : LoginViewState()
    data class Error(val message: String) : LoginViewState()
}

data class ProfileViewState(
    val profile: Profile? = null
)

