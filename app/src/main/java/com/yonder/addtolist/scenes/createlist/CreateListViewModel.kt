package com.yonder.addtolist.scenes.createlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.scenes.home.domain.usecase.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@HiltViewModel
class CreateListViewModel @Inject constructor(
  private val userListUseCase: UserListUseCase
) : ViewModel() {

  private val _state = MutableLiveData<CreateListViewState>()
  val state: LiveData<CreateListViewState> = _state

  fun createList(listName: String, listColor: String) {
    if (listName.trim().isEmpty()) {
      _state.value = CreateListViewState.EmptyListName
    } else {
      userListUseCase.createList(listName, listColor)
        .onEach { result ->
          result.onLoading {
            _state.value = CreateListViewState.Loading
          }.onSuccess {
            _state.value = CreateListViewState.ListCreated()
          }.onError {
            _state.value = CreateListViewState.ListCreated(it.toReadableMessage())
          }
        }
        .launchIn(viewModelScope)
    }

  }
}
