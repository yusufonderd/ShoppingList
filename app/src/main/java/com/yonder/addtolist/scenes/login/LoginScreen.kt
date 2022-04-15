package com.yonder.addtolist.scenes.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigator
import coil.compose.rememberImagePainter
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.scenes.login.row.AuthButton
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8
import timber.log.Timber

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    when  {
        uiState.shouldNavigateShoppingItems -> {
            LaunchedEffect(Unit) {
                navController.navigate(Screen.List.route)
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = colorResource(id = R.color.colorPrimary))
            .fillMaxSize()

    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.h4
        )

        Text(
            text = stringResource(id = R.string.app_description),
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(padding_8)
        )
        Image(
            rememberImagePainter(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_login_welcome
                )
            ),
            contentDescription = stringResource(id = R.string.cd_welcome_image),
            modifier = Modifier.padding(padding_16)
        )

        /*AuthButton(textResId = R.string.continue_with_google) {
           googleLogin(requireContext(),launcher)
        }

        AuthButton(textResId = R.string.continue_with_facebook) {
         // login.invoke()
            facebookLogin(requireContext(),viewModel )
        }*/

        AuthButton(textResId = R.string.continue_as_guest) {
            viewModel.continueAsGuest()
        }

    }
}
