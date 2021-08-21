package com.yonder.addtolist.core.base

import com.yonder.statelayout.State
import com.yonder.addtolist.core.network.responses.Result
/**
 * @author yusuf.onder
 * Created on 21.08.2021
 */

typealias LayoutState =  State

fun <T> Result<T>.getLayoutState(): LayoutState {
  return when (this) {
    is Result.Success -> {
      LayoutState.CONTENT
    }
    is Result.Error -> {
      LayoutState.ERROR
    }
    is Result.Loading -> {
      LayoutState.LOADING
    }
  }
}