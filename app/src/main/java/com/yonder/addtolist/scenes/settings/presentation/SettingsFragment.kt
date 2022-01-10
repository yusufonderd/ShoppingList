package com.yonder.addtolist.scenes.settings.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.review.ReviewManagerFactory
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.LoadingView
import com.yonder.addtolist.core.extensions.reviewApp
import com.yonder.addtolist.scenes.about.PRIVACY_POLICY_URL
import com.yonder.addtolist.scenes.premium.PremiumBottomSheetFragment
import com.yonder.addtolist.theme.padding_8
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

const val INSTAGRAM_URL = "https://www.instagram.com/addtolist.co/"
const val TWITTER_URL = "https://twitter.com/addtolistapp"

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

    private fun navigateUrl(url: String) {
        try {
            val browserIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            startActivity(browserIntent)
        } catch (e: Exception) {
            e.printStackTrace()
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
                                    R.string.be_premium -> {
                                        val bottomSheet = PremiumBottomSheetFragment()
                                        activity?.supportFragmentManager?.let {
                                            bottomSheet.show(it, bottomSheet.tag)
                                        }
                                    }
                                    R.string.twitter -> {
                                        navigateUrl(TWITTER_URL)
                                    }
                                    R.string.instagram -> {
                                        navigateUrl(INSTAGRAM_URL)
                                    }
                                    R.string.rate_us -> {
                                        activity?.reviewApp()
                                    }
                                    R.string.account -> {
                                        findNavController().navigate(SettingsFragmentDirections.actionSettingsToAccountDetail())
                                    }
                                    R.string.about -> {
                                        findNavController().navigate(SettingsFragmentDirections.actionSettingsToAbout())
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
                                  .padding(padding_8),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                imageDetail.leftPngResId?.let {
                                    Image(
                                        painter = painterResource(it),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.padding(end = padding_8)
                                    )
                                }

                                imageDetail.leftImageResId?.let {
                                    Icon(
                                        painter = painterResource(id = it),
                                        contentDescription = null,
                                        modifier = Modifier.padding(padding_8)
                                    )
                                }

                                imageDetail.leftTitleResId?.let {
                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        text = getString(it),
                                        modifier = Modifier.padding(padding_8)
                                    )
                                }

                                Text(
                                    text = getString(imageDetail.titleResId),
                                    color = Color.DarkGray,
                                    style = MaterialTheme.typography.body1
                                )

                                imageDetail.rightTitleResId?.let {
                                    Spacer(Modifier.weight(1f))
                                    Text(
                                        text = getString(it),
                                        color = Color.LightGray
                                    )
                                }
                            }
                        }
                        Divider(color = Color.LightGray)
                    }
                }
            }
            is SettingsUIState.Loading -> {
                LoadingView()
            }
        }
    }

}
