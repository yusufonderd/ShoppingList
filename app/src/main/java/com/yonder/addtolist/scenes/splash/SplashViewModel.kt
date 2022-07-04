package com.yonder.addtolist.scenes.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.domain.usecase.GetProductsWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val categoryUseCase: GetProductsWithCategory
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    sealed class Event {
        object NavigateToList : Event()
    }

    init {
        startSplashFlow()
    }

    private fun sendEvent(event: Event){
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    fun startSplashFlow() {
        _uiState.update { it.copy(shouldShowLoading = true, shouldShowError = false) }
        viewModelScope.launch {
            categoryUseCase.getCategoriesWithProducts().collect { result ->
                result.onSuccess {
                    sendEvent(Event.NavigateToList)
                }.onError {
                    _uiState.update { uiState ->
                        uiState.copy(
                            shouldShowLoading = false,
                            shouldShowError = true
                        )
                    }
                }
            }
        }
    }

    data class UiState(
        val shouldShowLoading: Boolean = false,
        val shouldShowError: Boolean = false,
    )

}
