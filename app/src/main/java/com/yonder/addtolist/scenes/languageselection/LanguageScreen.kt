package com.yonder.addtolist.scenes.languageselection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.getActivity
import com.yonder.addtolist.scenes.languageselection.row.LanguageRow
import com.yonder.addtolist.uicomponent.ErrorView
import com.yonder.addtolist.uicomponent.LoadingView
import com.yonder.addtolist.uicomponent.ThinDivider
import java.util.*

@Composable
fun LanguageScreen(navController: NavController) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.white))
            ) {
                items(languageUiState.languages) { language ->
                    Column {
                        LanguageRow(language = language) {
                            val config: Configuration =
                                context.resources.configuration
                            config.locale = Locale(language.tag)
                            context.resources.updateConfiguration(
                                config,
                                context.resources.displayMetrics
                            )
                            viewModel.setLocale(language.tag)
                            navController.popBackStack()
                            context.getActivity()?.recreate()
                        }
                        ThinDivider()
                    }
                }
            }
        }
    }
}