package com.yonder.addtolist.scenes.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.base.LayoutState
import com.yonder.addtolist.core.data.SingleLiveEvent
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.home.domain.usecase.GetUserListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListItemsViewModel @Inject constructor(
  private val getUserListsUseCase: GetUserListsUseCase
) : ViewModel() {

  private val _state: SingleLiveEvent<ShoppingListItemsViewState> =
    SingleLiveEvent()
  val state: SingleLiveEvent<ShoppingListItemsViewState> get() = _state

  fun getShoppingItems() {
    viewModelScope.launch {
      _state.value = ShoppingListItemsViewState.SetLayoutState(LayoutState.LOADING)
      getUserListsUseCase().collect { userLists ->
        _state.value = ShoppingListItemsViewState.SetLayoutState(LayoutState.CONTENT)
        if (userLists.isEmpty()) {
          _state.value = ShoppingListItemsViewState.CreateNewListContent
        } else {
          _state.value = ShoppingListItemsViewState.Result(userLists)
        }
      }
    }
  }
}

sealed class ShoppingListItemsViewState {
  data class SetLayoutState(val layoutState: LayoutState) : ShoppingListItemsViewState()
  object CreateNewListContent : ShoppingListItemsViewState()
  data class Result(var userLists: List<UserListWithProducts>) :
    ShoppingListItemsViewState()
}
