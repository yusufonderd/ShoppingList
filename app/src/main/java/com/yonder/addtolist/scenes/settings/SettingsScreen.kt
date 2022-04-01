package com.yonder.addtolist.scenes.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.getActivity
import com.yonder.addtolist.core.extensions.navigateUrl
import com.yonder.addtolist.core.extensions.reviewApp
import com.yonder.addtolist.scenes.accountdetail.ProfileImageView
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.scenes.premium.PremiumBottomSheetFragment
import com.yonder.addtolist.scenes.settings.presentation.SettingsUIState
import com.yonder.addtolist.scenes.settings.presentation.SettingsViewModel
import com.yonder.addtolist.theme.padding_4
import com.yonder.addtolist.theme.padding_8
import com.yonder.addtolist.theme.profile_image_size_small
import com.yonder.addtolist.uicomponent.LoadingView

@Composable
fun Settings(navController: NavController) {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val context = LocalContext.current
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
                                  /*  activity?.supportFragmentManager?.let {
                                        bottomSheet.show(it, bottomSheet.tag)
                                    }*/
                                }
                                R.string.twitter -> {
                                    context.getActivity()?.navigateUrl("https://twitter.com/addtolistapp")
                                }
                                R.string.instagram -> {
                                    context.getActivity()?.navigateUrl("https://www.instagram.com/addtolist.co/")
                                }
                                R.string.rate_us -> {
                                    context.getActivity()?.reviewApp()
                                }
                                R.string.account -> {
                                    navController.navigate(Screen.Account.route)
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

                            imageDetail.leftImageUrl?.let {
                                ProfileImageView(imageUrl = it)
                            }

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
                    Divider(color = Color.LightGray)
                }
            }
        }
        is SettingsUIState.Loading -> {
            LoadingView()
        }
    }

    @Composable
    fun ProfileImageView(imageUrl: String) {
        Box(modifier = Modifier.padding(end = padding_4)) {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = stringResource(R.string.cd_close_premium_screen),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(profile_image_size_small)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)

            )
        }
    }

}