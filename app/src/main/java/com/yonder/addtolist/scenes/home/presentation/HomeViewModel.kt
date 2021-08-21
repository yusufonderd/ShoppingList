package com.yonder.addtolist.scenes.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.base.LayoutState
import com.yonder.addtolist.core.base.getLayoutState
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.home.domain.usecase.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ShoppingListItemsViewModel @Inject constructor(
  private val userListUseCase: UserListUseCase
) : ViewModel() {

  private val _state: MutableStateFlow<ShoppingListItemsViewState> =
    MutableStateFlow(ShoppingListItemsViewState.SetLayoutState(LayoutState.LOADING))
  val state: StateFlow<ShoppingListItemsViewState> get() = _state

  fun getShoppingItems() {
    userListUseCase.getUserLists().onEach { result ->
      _state.value = ShoppingListItemsViewState.SetLayoutState(layoutState = result.getLayoutState())
      result.onSuccess { userLists ->
        if (userLists.isEmpty()) {
          _state.value = ShoppingListItemsViewState.CreateNewListContent
        } else {
          _state.value = ShoppingListItemsViewState.Result(userLists)
        }
      }
    }.launchIn(viewModelScope)
  }

}

sealed class ShoppingListItemsViewState {
  data class SetLayoutState(val layoutState : LayoutState): ShoppingListItemsViewState()
  object CreateNewListContent : ShoppingListItemsViewState()
  data class Result(var userLists: List<UserListWithProducts> = emptyList()) :
    ShoppingListItemsViewState()
}