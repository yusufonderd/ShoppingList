package com.yonder.addtolist.features.list.presentation

import androidx.lifecycle.ViewModel
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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