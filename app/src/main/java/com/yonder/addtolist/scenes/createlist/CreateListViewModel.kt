package com.yonder.addtolist.scenes.createlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.domain.usecase.CreateList
import com.yonder.addtolist.scenes.splash.SplashViewModel
import com.yonder.core.base.BaseViewModel
import com.yonder.core.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@HiltViewModel
class CreateListViewModel @Inject constructor(
    private val createList: CreateList
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    sealed class Event {
        object PopBackStack : Event()
        data class Message(var message: String) : Event()
    }

    private fun sendEvent(event: Event){
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    fun createList(listName: String, listColorName: String) {
        if (listName.isBlank()) {
            _uiState.update { it.copy(shouldShowBlankListNameError = true) }
        } else {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                createList(CreateList.Params(listName, listColorName)).collectLatest { result ->
                    result.onSuccess {
                        sendEvent(Event.PopBackStack)
                    }.onError { throwable ->
                        _uiState.update { it.copy(isLoading = false) }
                        sendEvent(Event.Message(throwable.toReadableMessage()))
                    }
                }
            }
        }
    }
    fun setBlankListState(text: String){
        if (text.isNotBlank()) {
            _uiState.update { it.copy(shouldShowBlankListNameError = false) }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val shouldShowBlankListNameError: Boolean = false,
        val errorMessage: String = ""
    )

}
