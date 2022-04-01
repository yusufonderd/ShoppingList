package com.yonder.addtolist.scenes.languageselection

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.getActivity
import com.yonder.addtolist.scenes.languageselection.row.LanguageRow
import com.yonder.addtolist.uicomponent.ErrorView
import com.yonder.addtolist.uicomponent.LoadingView

@Composable
fun LanguageScreen(navController: NavController){
    val viewModel = hiltViewModel<LanguageSelectionViewModel>()
    val context = LocalContext.current
    val languageUiState by viewModel.uiState.collectAsState()
    when {
        languageUiState.shouldShowError -> {
            ErrorView(centerText = stringResource(id = R.string.error_occurred)) {
                viewModel.getLanguages()
            }
        }
        languageUiState.shouldShowLoading -> {
            LoadingView()
        }
        else -> {
            LazyColumn {
                items(languageUiState.languages) { language ->
                    LanguageRow(language = language) {
                        viewModel.setLocale(language.tag)
                        context.getActivity()?.recreate()
                    }
                }
            }
        }
    }
}