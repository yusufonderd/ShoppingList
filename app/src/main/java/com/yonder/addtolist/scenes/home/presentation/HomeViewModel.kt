package com.yonder.addtolist.scenes.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.domain.usecase.GetUserLists
import com.yonder.addtolist.scenes.home.domain.model.UserListUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListItemsViewModel @Inject constructor(
    private val getUserListsUseCase: GetUserLists
) : ViewModel() {

    init {
        getShoppingItems()
    }

    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

    private fun getShoppingItems() {
        viewModelScope.launch {
            getUserListsUseCase().collect { userLists ->
                _uiState.update {
                    it.copy(userLists = userLists, isLoading = false)
                }
            }
        }
    }

    data class UiState(
        val isLoading: Boolean,
        var userLists: List<UserListUiModel> = emptyList()
    ) {
        val shouldShowAddListView get() = userLists.isEmpty()
    }

}