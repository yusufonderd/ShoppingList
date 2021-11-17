package com.yonder.addtolist.scenes.accountdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.domain.usecase.GetCurrentUserUseCase
import com.yonder.addtolist.scenes.login.domain.model.UserUiModel
import com.yonder.addtolist.scenes.splash.domain.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 21.07.2021
 */

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
  private val userInfoUseCase: UserInfoUseCase,
  private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

  private val _state: MutableStateFlow<AccountDetailViewState> =
    MutableStateFlow(AccountDetailViewState.Loading)
  val state: StateFlow<AccountDetailViewState> get() = _state

  init {
    fetchCurrentUser()
  }

  fun fetchCurrentUser() {
    viewModelScope.launch {
      getCurrentUserUseCase.invoke().collect {
        it.onSuccess { user ->
          _state.value = AccountDetailViewState.AccountInfo(user)
        }.onLoading {
          _state.value = AccountDetailViewState.Loading
        }.onError { throwable ->
          _state.value = AccountDetailViewState.Error(throwable.localizedMessage.orEmpty())
        }
      }
    }
  }

  fun logout() {
    userInfoUseCase.removeToken()
    _state.value = AccountDetailViewState.Logout
  }

}


sealed class AccountDetailViewState {
  object Loading : AccountDetailViewState()
  object Logout : AccountDetailViewState()
  data class AccountInfo(val userUIModel: UserUiModel) : AccountDetailViewState()
  data class Error(val error: String) : AccountDetailViewState()

}

