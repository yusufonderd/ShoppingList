package com.yonder.addtolist.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListItemsViewModel @Inject constructor(
  private val userPreferenceDataStore: UserPreferenceDataStore
) : ViewModel() {

  private val _shoppingListViewState: MutableStateFlow<ShoppingListItemsViewState> =
    MutableStateFlow(ShoppingListItemsViewState.Initial)
  val shoppingListViewState: StateFlow<ShoppingListItemsViewState> get() = _shoppingListViewState

  init {
    startInitialAppFlow()
  }

  private fun startInitialAppFlow() {
    viewModelScope.launch {
      userPreferenceDataStore.token
        .map { token -> token != null }
        .collect { isLoggedIn ->
        if (isLoggedIn.not()) {
          _shoppingListViewState.value = ShoppingListItemsViewState.GoLogin
        }
      }
    }
  }

}

sealed class ShoppingListItemsViewState {
  object Initial : ShoppingListItemsViewState()
  object GoSplash : ShoppingListItemsViewState()
  object GoLogin : ShoppingListItemsViewState()
}