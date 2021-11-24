package com.yonder.addtolist.scenes.splash.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.scenes.splash.domain.CategoriesUseCase
import com.yonder.addtolist.scenes.splash.domain.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val userInfoUseCase: UserInfoUseCase,
  private val categoryUseCase: CategoriesUseCase
) : ViewModel() {

  private val _state: MutableStateFlow<SplashViewState> = MutableStateFlow(SplashViewState.Loading)
  val state: StateFlow<SplashViewState> get() = _state

  init {
    getUuid()
    startSplashFlow()
  }

  private fun getUuid() { viewModelScope.launch {
      userInfoUseCase.getUuid().collect()
    }
  }

  private fun checkIsLoggedIn(){
    viewModelScope.launch {
      userInfoUseCase.isLoggedIn().collect { isLoggedIn ->
        _state.value = getNavigateDestination(isLoggedIn)
      }
    }
  }

  private fun startSplashFlow() {
    viewModelScope.launch {
      categoryUseCase.getCategories().collect { result ->
        result.onSuccess {
          checkIsLoggedIn()
        }.onError {
          Timber.e("onError => $it")
        }
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

}

sealed class SplashViewState {
  object Loading : SplashViewState()
  object GoLogin : SplashViewState()
  object GoShoppingListItems : SplashViewState()
}

