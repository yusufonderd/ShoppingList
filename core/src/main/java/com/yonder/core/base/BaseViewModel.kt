package com.yonder.core.base

/**
 * @author yusuf.onder
 * Created on 30.12.2021
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.core.network.RestResult
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

abstract class BaseViewModel<E : Event> : ViewModel() {

    private val _event = Channel<Event>(1)
    val effect = _event.receiveAsFlow().conflate()

    fun pushEvent(event: E) = viewModelScope.launch {
        _event.send(event)
    }

    @InternalCoroutinesApi
    fun <T> request(
        flow: Flow<RestResult<T>>,
        onSuccess: ((data: T) -> Unit)? = null,
        onError: ((t: Throwable) -> Unit)? = null,
        onLoading: (() -> Unit)? = null
    ) = viewModelScope.launch {
        flow.collect { result ->
            when (result) {
                is RestResult.Loading -> onLoading?.invoke()
                is RestResult.Success -> onSuccess?.invoke(result.data)
                is RestResult.Error -> {
                    onError?.invoke(result.throwable)
                }
            }
        }
    }

}

interface Event
