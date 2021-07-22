package com.yonder.addtolist.scenes.createlist

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

sealed class CreateListViewState {
  object EmptyListName : CreateListViewState()
  object Loading : CreateListViewState()
  object ListCreated : CreateListViewState()
  data class Error(val errorMessage : String) : CreateListViewState()
}

