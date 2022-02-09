package com.yonder.addtolist.scenes.createlist

import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.domain.usecase.CreateList
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
    private val createList: CreateList
) : BaseViewModel<CreateListViewModel.UiEvent>() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun createList(listName: String, listColor: String) {
        if (listName.isBlank()) {
            _uiState.update { it.copy(shouldShowBlankListNameError = true) }
        } else {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                createList(CreateList.Params(listName, listColor)).collectLatest { result ->
                    result.onSuccess {
                        pushEvent(UiEvent.ListCreated)
                    }.onError { throwable ->
                        pushEvent(UiEvent.Error(throwable.toReadableMessage()))
                    }
                }
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val shouldShowBlankListNameError: Boolean = false,
        val errorMessage: String = ""
    )

    sealed class UiEvent : Event {
        object Initial : UiEvent()
        object ListCreated : UiEvent()
        data class Error(var errorMessage: String) : UiEvent()
    }
}
