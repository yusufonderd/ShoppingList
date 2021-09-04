package com.yonder.addtolist.scenes.createlist

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

sealed class CreateListViewEvent {
  object ShowBlankListNameError : CreateListViewEvent()
  object Loading : CreateListViewEvent()
  object ListCreated : CreateListViewEvent()
}

