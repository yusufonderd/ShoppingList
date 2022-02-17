package com.yonder.addtolist.scenes.languageselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.languageselection.row.LanguageRow
import com.yonder.addtolist.uicomponent.ErrorView
import com.yonder.addtolist.uicomponent.LoadingView
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */

@AndroidEntryPoint
class LanguageSelectionFragment : Fragment() {

    private val viewModel: LanguageSelectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainContent()
            }
        }
    }

    private fun setLocale(languageCode: String) {
        viewModel.setLocale(languageCode)
        activity?.recreate()
    }

    @Composable
    fun MainContent() {
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
                            setLocale(languageCode = language.tag)
                        }
                    }
                }
            }
        }


    }

}
