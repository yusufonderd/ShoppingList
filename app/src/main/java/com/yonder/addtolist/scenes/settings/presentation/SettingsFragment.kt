package com.yonder.addtolist.scenes.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

  val viewModel: SettingsViewModel by viewModels()

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
    val settingsUIState by viewModel.state.collectAsState()

    when (settingsUIState) {
      is SettingsUIState.Initial -> {
        LazyColumn {
          items((settingsUIState as SettingsUIState.Initial).imageDetailList) { imageDetail ->
            TextButton(
              onClick = {
                when (imageDetail.titleResId) {
                  R.string.account -> {
                    findNavController().navigate(SettingsFragmentDirections.actionSettingsToAccountDetail())
                  }
                  R.string.language -> {
                    findNavController().navigate(SettingsFragmentDirections.actionSettingsToLanguageSelection())
                  }
                }
              }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
              ) {

                imageDetail.leftPngResId?.let {
                  Image(
                    painter = painterResource(it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(end = 8.dp)
                  )
                }

                imageDetail.leftImageResId?.let {
                  Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                  )
                }

                imageDetail.leftTitleResId?.let {
                  Text(
                    style = MaterialTheme.typography.body1,
                    text = getString(it),
                    modifier = Modifier.padding(8.dp)
                  )
                }

                Text(
                  text = getString(imageDetail.titleResId),
                  color = Color.DarkGray,
                  style = MaterialTheme.typography.body1)
              }

            }
            Divider(color = Color.LightGray)
          }
        }
      }
    }
  }

}
