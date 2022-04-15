package com.yonder.addtolist.scenes.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.domain.usecase.GetCurrentUser
import com.yonder.addtolist.domain.usecase.GetProductsWithCategory
import com.yonder.addtolist.domain.usecase.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase,
    private val categoryUseCase: GetProductsWithCategory,
    private val getCurrentUser: GetCurrentUser
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        startSplashFlow()
    }

    private fun checkIsLoggedIn() {
        viewModelScope.launch {
            getCurrentUser().collect { result ->
                result.onSuccessOrError {
                    val isLoggedIn: Boolean = userInfoUseCase.isLoggedIn()
                    if (isLoggedIn) {
                        _uiState.update {
                            it.copy(
                                shouldNavigateListScreen = true,
                                shouldShowError = false,
                                shouldShowLoading = false
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                shouldNavigateLoginScreen = true,
                                shouldShowError = false,
                                shouldShowLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun startSplashFlow() {
        _uiState.update { it.copy(shouldShowLoading = true, shouldShowError = false) }
        userInfoUseCase.getUuid()
        viewModelScope.launch {
            categoryUseCase.getCategoriesWithProducts().collect { result ->
                result.onSuccess {
                    checkIsLoggedIn()
                }.onError {
                    _uiState.update { uiState ->
                        uiState.copy(
                            shouldShowLoading = false,
                            shouldShowError = true
                        )
                    }
                }
            }
        }
    }

    data class UiState(
        val shouldShowLoading: Boolean = false,
        val shouldShowError: Boolean = false,
        val shouldNavigateLoginScreen: Boolean = false,
        val shouldNavigateListScreen: Boolean = false
    )

}
