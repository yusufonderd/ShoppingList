package com.yonder.addtolist.presentation.splash

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val userPreferenceDataStore: UserPreferenceDataStore
) : ViewModel() {

  private val _state: MutableStateFlow<SplashViewState> =
    MutableStateFlow(SplashViewState.Initial)
  val state: StateFlow<SplashViewState> get() = _state

  init {
    startInitialAppFlow()
  }

  private fun startInitialAppFlow(splashDelay: Long = DELAY_SPLASH) {
    viewModelScope.launch {
      delay(splashDelay)
      userPreferenceDataStore.token
        .map { token -> token != null }
        .onEach { isLoggedIn ->
          _state.value = getStateOf(isLoggedIn)
        }
        .launchIn(viewModelScope)
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  fun getStateOf(isLoggedIn: Boolean): SplashViewState {
    return if (isLoggedIn) {
      SplashViewState.GoHome
    } else {
      SplashViewState.GoLogin
    }
  }

  companion object {
    const val DELAY_SPLASH = 1000L * 2
  }

}

sealed class SplashViewState {
  object Initial : SplashViewState()
  object GoLogin : SplashViewState()
  object GoHome : SplashViewState()
}

