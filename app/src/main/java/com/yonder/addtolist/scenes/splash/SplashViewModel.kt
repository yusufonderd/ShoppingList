package com.yonder.addtolist.scenes.splash

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.domain.usecase.GetCurrentUser
import com.yonder.addtolist.domain.usecase.GetProductsWithCategory
import com.yonder.addtolist.domain.usecase.UserInfoUseCase
import com.yonder.core.base.BaseViewModel
import com.yonder.core.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val userInfoUseCase: UserInfoUseCase,
  private val categoryUseCase: GetProductsWithCategory,
  private val getCurrentUser: GetCurrentUser
  ) : BaseViewModel<SplashViewModel.UiEvent>() {

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
        getCurrentUser().collect { result ->
          result.onSuccessOrError {
            pushEvent(getNavigateDestination(isLoggedIn))
          }
        }
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
  fun getNavigateDestination(isLoggedIn: Boolean): UiEvent {
    return if (isLoggedIn) {
      UiEvent.ShoppingListItems
    } else {
      UiEvent.Login
    }
  }

  sealed class UiEvent : Event {
    object Loading : UiEvent()
    object Login : UiEvent()
    object ShoppingListItems : UiEvent()
  }

}
