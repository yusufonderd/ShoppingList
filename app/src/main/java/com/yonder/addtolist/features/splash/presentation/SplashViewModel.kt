package com.yonder.addtolist.features.splash.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.features.splash.domain.SplashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val splashUseCase: SplashUseCase
) : ViewModel() {

  private val _state: MutableStateFlow<SplashViewState> = MutableStateFlow(SplashViewState.Loading)
  val state: StateFlow<SplashViewState> get() = _state

  init {
    getUuid()
    startSplashFlow()
  }

  private fun getUuid() {
    viewModelScope.launch {
      splashUseCase.getUuid().collect()
    }
  }

  private fun startSplashFlow(splashDelay: Long = DELAY_SPLASH) {
    viewModelScope.launch {
      delay(splashDelay)
      splashUseCase.isLoggedIn().collect { isLoggedIn ->
        _state.value = getNavigateDestination(isLoggedIn)
      }
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  fun getNavigateDestination(isLoggedIn: Boolean): SplashViewState {
    return if (isLoggedIn) {
      SplashViewState.GoShoppingListItems
    } else {
      SplashViewState.GoLogin
    }
  }

  companion object {
    const val DELAY_SPLASH = 1000L * 2
  }

}

sealed class SplashViewState {
  object Loading : SplashViewState()
  object GoLogin : SplashViewState()
  object GoShoppingListItems : SplashViewState()
}

