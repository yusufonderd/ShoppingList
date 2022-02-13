package com.yonder.addtolist.scenes.languageselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.data.doOnError
import com.yonder.addtolist.core.data.doOnSuccess
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.scenes.languageselection.domain.GetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */

@HiltViewModel
class LanguageSelectionViewModel @Inject constructor(
    private val getLanguageUseCase: GetLanguageUseCase,
    var userPreferenceDataStore: UserPreferenceDataStore
) : ViewModel() {

    init {
        getLanguages()
    }

    private val _state: MutableStateFlow<LanguageSelectionViewEvent> =
        MutableStateFlow(LanguageSelectionViewEvent.Initial)
    val state: StateFlow<LanguageSelectionViewEvent> get() = _state

    private fun getLanguages() {
        viewModelScope.launch {
            getLanguageUseCase()
                .doOnSuccess {
                    _state.value = LanguageSelectionViewEvent.Load(it)
                }.doOnError {
                    _state.value = LanguageSelectionViewEvent.Error(it.localizedMessage.orEmpty())
                }
                .launchIn(viewModelScope)
        }
    }

    fun setLocale(languageCode: String) {
        userPreferenceDataStore.setLocale(Locale(languageCode))
    }

}
