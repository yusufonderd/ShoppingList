package com.yonder.addtolist.scenes.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.uicomponent.LoadingView

@Composable
fun SplashScreen (navController: NavController){
    val viewModel = hiltViewModel<SplashViewModel>()

    val event by viewModel.effect.collectAsState(initial = SplashViewModel.UiEvent.Loading)

    when (event) {
        is SplashViewModel.UiEvent.Login -> {
            navController.navigate(Screen.Login.route)
        }
        is SplashViewModel.UiEvent.ShoppingListItems -> {
            navController.navigate(Screen.List.route)
        }
        is SplashViewModel.UiEvent.Loading -> {
            LoadingView()
        }
    }
}