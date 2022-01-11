package com.yonder.addtolist.scenes.createlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.scenes.createlist.domain.CreateListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@HiltViewModel
class CreateListViewModel @Inject constructor(
  private val createListUseCase: CreateListUseCase
) : ViewModel() {

  private val _state = MutableLiveData<CreateListViewEvent>()
  val event: LiveData<CreateListViewEvent> = _state

  fun createList(listName: String, listColor: String) {
    if (listName.isBlank()) {
      _state.value = CreateListViewEvent.ShowBlankListNameError
    } else {
      _state.value = CreateListViewEvent.Loading
      viewModelScope.launch {
        createListUseCase(listName, listColor).collectLatest {
          _state.value = CreateListViewEvent.ListCreated
        }
      }
    }
  }
}
