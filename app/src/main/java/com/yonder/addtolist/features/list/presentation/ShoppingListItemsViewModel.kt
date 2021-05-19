package com.yonder.addtolist.features.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.NetworkResult
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.features.list.domain.model.CategoryProductsUiModel
import com.yonder.addtolist.features.list.domain.usecase.ShoppingItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ShoppingListItemsViewModel @Inject constructor(
  private val shoppingItemsUseCase: ShoppingItemsUseCase
) : ViewModel() {

  private val _shoppingListViewState: MutableStateFlow<ShoppingListItemsViewState> =
    MutableStateFlow(ShoppingListItemsViewState.Initial)
  val shoppingListViewState: StateFlow<ShoppingListItemsViewState> get() = _shoppingListViewState


  init {
    getShoppingItems()
  }

  private fun getShoppingItems() {
    shoppingItemsUseCase
      .fetchShoppingItems(null)
      .onEach { result ->
      _shoppingListViewState.value = ShoppingListItemsViewState.Result(result)
    }.launchIn(viewModelScope)
  }

}

sealed class ShoppingListItemsViewState {
  object Initial : ShoppingListItemsViewState()
  data class Result(var networkResult: NetworkResult<CategoryProductsUiModel>) : ShoppingListItemsViewState()
  object GoLogin : ShoppingListItemsViewState()
}