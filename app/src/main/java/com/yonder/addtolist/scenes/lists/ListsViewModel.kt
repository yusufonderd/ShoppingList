package com.yonder.addtolist.scenes.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.domain.usecase.GetUserLists
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getUserListsUseCase: GetUserLists
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

    fun getShoppingItems() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getUserListsUseCase().collect { userLists ->
                _uiState.update {
                    it.copy(userLists = userLists.filterNotNull(), isLoading = false)
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