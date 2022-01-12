package com.yonder.addtolist.core.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */
/*
suspend fun <T> collect(
    flow: Flow<RestResult<T>>,
    onSuccess: (data: T) -> Unit
) = flow.collectLatest {
    if (it is RestResult.Success) {
        onSuccess(it.data)
    }
}
*/