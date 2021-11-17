package com.yonder.addtolist.scenes.languageselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yonder.addtolist.common.ui.LoadingView
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

  @Composable
  fun MainContent() {
    val languageUiState by viewModel.state.collectAsState()
    when (languageUiState) {
      is LanguageSelectionViewEvent.Load -> {
        LazyColumn {
          items((languageUiState as LanguageSelectionViewEvent.Load).languages) { language ->
            TextButton(
              onClick = {

              }
            ) {
              Text(text = language.name,
                color = Color.DarkGray,
                modifier = Modifier
                  .fillMaxSize()
                  .padding(bottom = 8.dp)
                  .padding(horizontal = 8.dp)
                  .align(Alignment.Bottom)
              )
            }
          }
        }
      }
      else -> {
        LoadingView()
      }

    }

  }

}
