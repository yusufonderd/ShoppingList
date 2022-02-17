package com.yonder.addtolist.scenes.languageselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.common.enums.AppLanguage
import com.yonder.addtolist.core.data.doOnError
import com.yonder.addtolist.core.data.doOnSuccess
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.domain.uimodel.LanguageUiModel
import com.yonder.addtolist.scenes.createlist.CreateListViewModel
import com.yonder.addtolist.scenes.languageselection.domain.GetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
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

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        getLanguages()
    }

    internal fun getLanguages() {
        _uiState.update { it.copy(shouldShowLoading = true) }
        viewModelScope.launch {
            getLanguageUseCase()
                .doOnSuccess { languages ->
                    _uiState.update {
                        it.copy(
                            languages = languages,
                            shouldShowLoading = false,
                            shouldShowError = false
                        )
                    }
                }.doOnError {
                    _uiState.update {
                        it.copy(
                            shouldShowError = true
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun setLocale(languageCode: String) {
        userPreferenceDataStore.setLocale(Locale(languageCode))
        userPreferenceDataStore.setAppLanguageId(AppLanguage.find(languageTag = languageCode).languageId)
    }


    data class UiState(
        val shouldShowLoading: Boolean = false,
        val shouldShowError: Boolean = false,
        val languages: List<LanguageUiModel> = emptyList()
    )

}
