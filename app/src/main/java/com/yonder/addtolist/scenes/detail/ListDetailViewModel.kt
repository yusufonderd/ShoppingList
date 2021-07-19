package com.yonder.addtolist.scenes.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.scenes.detail.domain.CategoryListUseCase
import com.yonder.addtolist.scenes.list.domain.usecase.UserListUseCase
import com.yonder.addtolist.scenes.list.presentation.ShoppingListItemsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@HiltViewModel
class ListDetailViewModel @Inject constructor(
  private val userListUseCase: CategoryListUseCase
  ) : ViewModel() {

  private val _state: MutableStateFlow<ListDetailViewState> =
    MutableStateFlow(ListDetailViewState.ShowContent)
  val state: StateFlow<ListDetailViewState> get() = _state

  init {
    fetchCategoriesIfRequired()
  }

  private fun fetchCategoriesIfRequired() {
    userListUseCase.getCategories()
      .onEach { result ->
        result.onSuccess {
          _state.value = ListDetailViewState.ShowContent
          fetchCategoriesIfRequired()
        }.onLoading {
          _state.value = ListDetailViewState.Loading
        }.onError { error ->
          _state.value = ListDetailViewState.Error(error.toReadableMessage())
        }
      }.launchIn(viewModelScope)
  }

}


sealed class ListDetailViewState {
  object Loading : ListDetailViewState()
  object ShowContent : ListDetailViewState()
  data class Error(var errorMessage: String) : ListDetailViewState()
}