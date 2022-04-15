package com.yonder.addtolist.scenes.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.uicomponent.ErrorView
import com.yonder.addtolist.uicomponent.LoadingView

@Composable
fun SplashScreen(navController: NavController) {
    val viewModel = hiltViewModel<SplashViewModel>()

    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.shouldShowLoading -> {
            LoadingView()
        }

        uiState.shouldShowError -> {
            ErrorView(centerText = stringResource(id = R.string.error_occurred)) {
                viewModel.startSplashFlow()
            }
        }

        uiState.shouldNavigateListScreen -> {
            navController.navigate(Screen.List.route)
        }

        uiState.shouldNavigateLoginScreen -> {
            navController.navigate(Screen.Login.route)
        }
    }


}




