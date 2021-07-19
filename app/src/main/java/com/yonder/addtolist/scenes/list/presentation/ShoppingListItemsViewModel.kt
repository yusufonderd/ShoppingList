package com.yonder.addtolist.scenes.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.scenes.list.domain.usecase.UserListUseCase
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

  private val _shoppingListViewState: MutableStateFlow<ShoppingListItemsViewState> =
    MutableStateFlow(ShoppingListItemsViewState.Loading)
  val shoppingListViewState: StateFlow<ShoppingListItemsViewState> get() = _shoppingListViewState

  fun getShoppingItems() {
    userListUseCase.getUserList().onEach { result ->
      result.onSuccess { userLists ->
        if (userLists.isEmpty()) {
          _shoppingListViewState.value = ShoppingListItemsViewState.CreateNewListContent
        } else {
          _shoppingListViewState.value = ShoppingListItemsViewState.Result(userLists)
        }
      }.onError { error ->
        _shoppingListViewState.value =
          ShoppingListItemsViewState.Error(error.toReadableMessage())
      }
    }.launchIn(viewModelScope)
  }

}

sealed class ShoppingListItemsViewState {
  object Loading : ShoppingListItemsViewState()
  object CreateNewListContent : ShoppingListItemsViewState()
  data class Result(var userLists: List<UserListEntity>) : ShoppingListItemsViewState()
  data class Error(var errorMessage: String) : ShoppingListItemsViewState()
}