package com.yonder.addtolist.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListItemsViewModel @Inject constructor(
  private val userPreferenceDataStore: UserPreferenceDataStore
) : ViewModel() {

  private val _shoppingListViewState: MutableStateFlow<ShoppingListItemsViewState> =
    MutableStateFlow(ShoppingListItemsViewState.Initial)
  val shoppingListViewState: StateFlow<ShoppingListItemsViewState> get() = _shoppingListViewState



}

sealed class ShoppingListItemsViewState {
  object Initial : ShoppingListItemsViewState()
  object GoSplash : ShoppingListItemsViewState()
  object GoLogin : ShoppingListItemsViewState()
}