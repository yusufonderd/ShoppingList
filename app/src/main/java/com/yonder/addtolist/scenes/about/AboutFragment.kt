package com.yonder.addtolist.scenes.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_8
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.res.colorResource
import androidx.navigation.fragment.findNavController


/**
 * @author yusuf.onder
 * Created on 10.01.2022
 */
const val PRIVACY_POLICY_URL =
    "https://gist.github.com/yusufonderd/6214999f3540e0645c07730171dec16b"

@AndroidEntryPoint
class AboutFragment : Fragment() {

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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding_8)
        ) {
            Text(
                text = getString(R.string.about_description),
                style = MaterialTheme.typography.body1,
                color= colorResource(id = R.color.primaryTextColor)
            )
            Divider()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = padding_8)
            ) {
                Text(
                    text = getString(R.string.version),
                    style = MaterialTheme.typography.body1,
                    color= colorResource(id = R.color.primaryTextColor)
                )
                Text(
                    text = BuildConfig.VERSION_NAME,
                    style = MaterialTheme.typography.body1,
                    color= colorResource(id = R.color.primaryTextColor)
                )
            }

            Divider()

            TextButton(
                onClick = {
                    val browserIntent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(PRIVACY_POLICY_URL)
                        )
                    startActivity(browserIntent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = getString(R.string.privacy_policy),
                    color = colorResource(id = R.color.purple_500)
                )
            }

            Divider()

            TextButton(
                onClick = {
                    findNavController().navigate(AboutFragmentDirections.actionSettingsToLicense())
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = getString(R.string.license),
                    color = colorResource(id = R.color.purple_500)
                )
            }

            Divider()

        }
    }


}
