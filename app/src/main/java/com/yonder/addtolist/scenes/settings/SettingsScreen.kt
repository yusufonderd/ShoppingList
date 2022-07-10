package com.yonder.addtolist.scenes.settings

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.app.ShareCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.navigate
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.theme.padding_8
import com.yonder.addtolist.uicomponent.LoadingView
import com.yonder.addtolist.uicomponent.ThinDivider

@Composable
fun Settings(navController: NavController) {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val context = LocalContext.current
    val settingsUIState by viewModel.state.collectAsState()


    when (settingsUIState) {
        is SettingsUIState.Initial -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.white))
            ) {
                items((settingsUIState as SettingsUIState.Initial).imageDetailList) { imageDetail ->
                    TextButton(
                        onClick = {
                            when (imageDetail.titleResId) {
                                R.string.be_premium -> {
                                    navController.navigate(Screen.Premium.route)
                                }
                                R.string.twitter -> {
                                    context.navigate("https://twitter.com/addtolistapp")
                                }
                                R.string.instagram -> {
                                    context.navigate("https://www.instagram.com/addtolist.co/")
                                }
                                R.string.rate_us -> {
                                    (context as Activity).startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
                                        )
                                    )
                                }
                                R.string.account -> {
                                    navController.navigate(Screen.Account.route)
                                }
                                R.string.share_with_friends -> {
                                    val activity = context as Activity
                                    ShareCompat.IntentBuilder.from(activity)
                                        .setType("text/plain")
                                        .setChooserTitle(context.getString(R.string.share_with_friends))
                                        .setText("http://play.google.com/store/apps/details?id=" + activity.packageName)
                                        .startChooser();
                                }
                                R.string.about -> {
                                    navController.navigate(Screen.About.route)
                                }
                                R.string.language -> {
                                    navController.navigate(Screen.Language.route)
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
                                    tint = colorResource(id = R.color.purple_500),
                                    modifier = Modifier.padding(padding_8)
                                )
                            }

                            imageDetail.leftTitleResId?.let {
                                Text(
                                    style = MaterialTheme.typography.body1,
                                    text = stringResource(it),
                                    modifier = Modifier.padding(padding_8),
                                    color = colorResource(id = R.color.primaryTextColor)
                                )
                            }

                            Text(
                                text = stringResource(imageDetail.titleResId),
                                color = colorResource(id = R.color.primaryTextColor),
                                style = MaterialTheme.typography.body1
                            )

                            imageDetail.rightTitleResId?.let {
                                Spacer(Modifier.weight(1f))
                                Text(
                                    text = stringResource(it),
                                    color = Color.LightGray
                                )
                            }
                            imageDetail.rightTitle?.let {
                                Spacer(Modifier.weight(1f))
                                Text(
                                    text = it,
                                    color = Color.LightGray
                                )
                            }
                        }
                    }
                    ThinDivider()
                }
            }
        }
        is SettingsUIState.Loading -> {
            LoadingView()
        }
    }


}