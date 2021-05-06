package com.yonder.addtolist.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListItemsViewModel @Inject constructor(
  private val userPreferenceDataStore: UserPreferenceDataStore
) : ViewModel() {

  private val _splashViewState: MutableStateFlow<ShoppingListItemsViewState> =
    MutableStateFlow(ShoppingListItemsViewState.Initial)
  val splashViewState: StateFlow<ShoppingListItemsViewState> get() = _splashViewState


  init {
    startInitialAppFlow()
  }

  private fun startInitialAppFlow() {
    viewModelScope.launch {
      userPreferenceDataStore.token
        .map { token -> token != null }
        .collect { isLoggedIn ->
        if (isLoggedIn.not()) {
          _splashViewState.value = ShoppingListItemsViewState.GoSplash
        }
      }
    }
  }

}

sealed class ShoppingListItemsViewState {
  object Initial : ShoppingListItemsViewState()
  object GoSplash : ShoppingListItemsViewState()
}