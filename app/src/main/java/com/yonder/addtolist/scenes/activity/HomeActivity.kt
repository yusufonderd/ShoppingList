package com.yonder.addtolist.scenes.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.about.AboutScreen
import com.yonder.addtolist.scenes.accountdetail.AccountScreen
import com.yonder.addtolist.scenes.createlist.CreateNewList
import com.yonder.addtolist.scenes.home.ListScreen
import com.yonder.addtolist.scenes.languageselection.LanguageScreen
import com.yonder.addtolist.scenes.listdetail.ListDetailScreen
import com.yonder.addtolist.scenes.login.LoginScreen
import com.yonder.addtolist.scenes.settings.Settings
import com.yonder.addtolist.scenes.splash.SplashScreen
import com.yonder.addtolist.theme.BreakingBadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreakingBadTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
                    val topBarState = rememberSaveable { (mutableStateOf(true)) }
                    val backArrowState = rememberSaveable { (mutableStateOf(false)) }

                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    when (navBackStackEntry?.destination?.route) {
                        Route.SPLASH, Route.LOGIN -> {
                            topBarState.value = false
                            bottomBarState.value = false
                        }
                        Route.LIST, Route.SETTINGS -> {
                            backArrowState.value = false
                            topBarState.value = true
                            bottomBarState.value = true
                        }
                        else -> {
                            topBarState.value = true
                            bottomBarState.value = true
                            backArrowState.value = true
                        }
                    }

                    Scaffold(
                        topBar = {
                            if (topBarState.value) {
                                TopAppBar(
                                    title = { Text(text = getString(R.string.app_name)) },
                                    navigationIcon = if (backArrowState.value) {
                                        {
                                            IconButton(onClick = { navController.navigateUp() }) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowBack,
                                                    contentDescription = "Back"
                                                )
                                            }
                                        }
                                    } else {
                                        null
                                    }
                                )
                            }
                        },
                        bottomBar = {
                            if (bottomBarState.value) {
                                BottomNavigation {
                                    val currentRoute =
                                        navController.currentBackStackEntry?.destination?.route
                                    items.forEach { screen ->
                                        BottomNavigationItem(
                                            icon = {
                                                Icon(
                                                    imageVector = screen.icon,
                                                    contentDescription = null,
                                                )
                                            },
                                            label = { Text(stringResource(screen.resourceId)) },
                                            selected = currentRoute == screen.route,
                                            onClick = {
                                                navController.navigate(screen.route) {
                                                    popUpTo(navController.graph.startDestinationId)
                                                    launchSingleTop = true
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            NavHost(navController, startDestination = Screen.Splash.route) {
                                composable(Screen.Login.route) { LoginScreen(navController) }
                                composable(Screen.Splash.route) { SplashScreen(navController) }
                                composable(Screen.List.route) { ListScreen(navController) }
                                composable(Screen.Settings.route) { Settings(navController) }
                                composable(Screen.CreateNewList.route) { CreateNewList(navController) }
                                composable(Screen.Language.route) { LanguageScreen(navController) }
                                composable(Screen.About.route) { AboutScreen() }
                                composable(Screen.Account.route) { AccountScreen() }
                                composable(Screen.ListDetail.route) { ListDetailScreen(navController) }
                            }
                        }
                    }
                }
            }
        }
    }
}

val items = listOf(
    Screen.List,
    Screen.Settings
)

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Login : Screen(Route.LOGIN, R.string.title_login, Icons.Filled.List)
    object Splash : Screen(Route.SPLASH, R.string.title_splash, Icons.Filled.List)
    object List : Screen(Route.LIST, R.string.title_list, Icons.Filled.List)
    object Settings : Screen(Route.SETTINGS, R.string.title_settings, Icons.Filled.Settings)
    object CreateNewList :
        Screen(Route.CREATE_NEW_LIST, R.string.create_new_list, Icons.Filled.List)

    object About : Screen(Route.ABOUT, R.string.title_about, Icons.Filled.Settings)
    object Account : Screen(Route.ACCOUNT, R.string.title_account_detail, Icons.Filled.Settings)
    object Language :
        Screen(Route.LANGUAGE, R.string.title_language_selection, Icons.Filled.Settings)
    object ListDetail : Screen(Route.LIST_DETAIL, R.string.title_list_detail, Icons.Filled.Settings)

}


object Route {
    const val CREATE_NEW_LIST = "createNewList"
    const val SETTINGS = "settings"
    const val LIST = "list"
    const val LIST_DETAIL = "list_detail"
    const val ABOUT = "about"
    const val ACCOUNT = "account"
    const val LANGUAGE = "language"
    const val SPLASH = "splash"
    const val LOGIN = "login"
}



