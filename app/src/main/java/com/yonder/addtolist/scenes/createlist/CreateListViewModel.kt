package com.yonder.addtolist.scenes.createlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.scenes.home.domain.usecase.CreateListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
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

  private val _state = MutableLiveData<CreateListViewState>()
  val state: LiveData<CreateListViewState> = _state

  fun createList(listName: String, listColor: String) {
    if (listName.isBlank()) {
      _state.value = CreateListViewState.ShowBlankListNameError
    } else {
      _state.value = CreateListViewState.Loading
      viewModelScope.launch {
        createListUseCase.invoke(listName, listColor).collect {
          _state.value = CreateListViewState.ListCreated
        }
      }
    }
  }
}
