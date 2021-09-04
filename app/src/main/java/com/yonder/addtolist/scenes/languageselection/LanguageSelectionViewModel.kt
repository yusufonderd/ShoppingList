package com.yonder.addtolist.scenes.languageselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.data.State
import com.yonder.addtolist.core.data.doOnSuccess
import com.yonder.addtolist.scenes.languageselection.domain.GetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */

@HiltViewModel
class LanguageSelectionViewModel @Inject constructor(
  private val getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {

  init {
    getLanguages()
  }
  private val _state: MutableStateFlow<LanguageSelectionViewEvent> =
    MutableStateFlow(LanguageSelectionViewEvent.SetLayoutState(State.Loading))
  val state: StateFlow<LanguageSelectionViewEvent> get() = _state

  fun getLanguages() {
    viewModelScope.launch {
      getLanguageUseCase()
        .doOnSuccess {
          _state.value = LanguageSelectionViewEvent.Load(it)
        }
        .onEach {
          _state.value = LanguageSelectionViewEvent.SetLayoutState(it)
        }
        .launchIn(viewModelScope)
    }
  }

}
