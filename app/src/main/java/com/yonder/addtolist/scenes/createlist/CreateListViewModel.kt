package com.yonder.addtolist.scenes.createlist

import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.scenes.createlist.domain.CreateListUseCase
import com.yonder.core.base.BaseViewModel
import com.yonder.core.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@HiltViewModel
class CreateListViewModel @Inject constructor(
    private val createListUseCase: CreateListUseCase
) : BaseViewModel<CreateListViewModel.UiEvent>() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun createList(listName: String, listColor: String) {
        if (listName.isBlank()) {
            _uiState.update { it.copy(shouldShowBlankListNameError = true) }
        } else {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                createListUseCase(listName, listColor).collectLatest {
                    pushEvent(UiEvent.ListCreated)
                }
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val shouldShowBlankListNameError: Boolean = false
    )

    sealed class UiEvent : Event {
        object ListCreated : UiEvent()
    }
}
