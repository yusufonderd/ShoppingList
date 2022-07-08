package com.yonder.addtolist.scenes.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.uicomponent.ErrorView
import com.yonder.addtolist.uicomponent.LoadingView
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(navController: NavController) {
    val viewModel = hiltViewModel<SplashViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.eventsFlow.collectLatest { value ->
            when(value) {
                SplashViewModel.Event.NavigateToList -> {
                    navController.navigate(Screen.Lists.route){
                        popUpTo(Screen.Splash.route){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    when {
        uiState.shouldShowLoading -> {
            LoadingView()
        }

        uiState.shouldShowError -> {
            ErrorView(centerText = stringResource(id = R.string.error_occurred)) {
                viewModel.startSplashFlow()
            }
        }
    }

}




