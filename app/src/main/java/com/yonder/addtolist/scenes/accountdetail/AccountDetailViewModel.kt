package com.yonder.addtolist.scenes.accountdetail

import androidx.lifecycle.ViewModel
import com.yonder.addtolist.scenes.splash.domain.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
/**
 * @author yusuf.onder
 * Created on 21.07.2021
 */

@HiltViewModel
class AccountDetailViewModel @Inject constructor(val userInfoUseCase: UserInfoUseCase) :
  ViewModel() {

  private val _state: MutableStateFlow<AccountDetailViewState> =
    MutableStateFlow(AccountDetailViewState.Initial)
  val state: StateFlow<AccountDetailViewState> get() = _state

  fun logout() {
    userInfoUseCase.removeToken()
    _state.value = AccountDetailViewState.Logout
  }

}


sealed class AccountDetailViewState {
  object Initial : AccountDetailViewState()
  object Logout : AccountDetailViewState()
}

