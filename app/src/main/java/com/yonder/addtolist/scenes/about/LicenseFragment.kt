package com.yonder.addtolist.scenes.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_8
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yusuf.onder
 * Created on 10.01.2022
 */

const val FREEPIK_URL =
    "https://www.freepik.com/"

@AndroidEntryPoint
class LicenseFragment : Fragment() {

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
        LazyColumn() {
            item {
                LicenceContent()
            }
        }
    }

    @Composable
    fun LicenceContent() {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier
                .padding(8.dp)
                .clickable(onClick = {
                    val browserIntent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(FREEPIK_URL)
                        )
                    startActivity(browserIntent)
                }),
            elevation = 8.dp
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = padding_8)
            ) {
                Column() {
                    Text(
                        text = getString(R.string.license_1_title),
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = getString(R.string.license_1_description),
                        style = MaterialTheme.typography.body1
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_chevron_right_24),
                    contentDescription = getString(R.string.cd_navigate_url),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(colorResource(R.color.primaryTextColor))
                )

            }
        }

    }

}