package com.yonder.core.base

/**
 * @author yusuf.onder
 * Created on 30.12.2021
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.core.network.RestResult
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

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
                is RestResult.Error -> { onError?.invoke(result.throwable) }
            }
        }
    }
}
