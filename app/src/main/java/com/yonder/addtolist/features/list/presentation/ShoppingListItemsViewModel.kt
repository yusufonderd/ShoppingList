package com.yonder.addtolist.features.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.features.list.domain.model.CategoryProductsUiModel
import com.yonder.addtolist.features.list.domain.usecase.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShoppingListItemsViewModel @Inject constructor(
  private val userListUseCase: UserListUseCase
) : ViewModel() {

  private val _shoppingListViewState: MutableStateFlow<ShoppingListItemsViewState> =
    MutableStateFlow(ShoppingListItemsViewState.Initial)
  val shoppingListViewState: StateFlow<ShoppingListItemsViewState> get() = _shoppingListViewState


  init {
    getShoppingItems()
  }

  private fun getShoppingItems() {
    Timber.d("adl-111 getShoppingItems")
    userListUseCase.getUserList().onEach {  result ->
      result.onSuccess { userListEntity ->
        if (userListEntity.isEmpty()){
          _shoppingListViewState.value = ShoppingListItemsViewState.CreateNewListContent
        }
        Timber.d("userListEntity => ${userListEntity.size}")
      }.onError { throwable ->
        _shoppingListViewState.value = ShoppingListItemsViewState.Error(throwable.toReadableMessage())
      }
    }.launchIn(viewModelScope)

   /* shoppingItemsUseCase
      .fetchShoppingItems(null)
      .onEach { result ->
        result.onSuccess { uiModel ->
          _shoppingListViewState.value = ShoppingListItemsViewState.Result(uiModel)
        }.onError { throwable ->
          _shoppingListViewState.value = ShoppingListItemsViewState.Error(throwable.toReadableMessage())
        }
    }.launchIn(viewModelScope)*/
  }

}

sealed class ShoppingListItemsViewState {
  object Initial : ShoppingListItemsViewState()
  object CreateNewListContent : ShoppingListItemsViewState()
  data class Result(var categories: CategoryProductsUiModel) : ShoppingListItemsViewState()
  data class Error (var errorMessage: String): ShoppingListItemsViewState()

}