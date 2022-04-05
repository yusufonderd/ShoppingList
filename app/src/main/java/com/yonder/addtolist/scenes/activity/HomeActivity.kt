package com.yonder.addtolist.scenes.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.about.AboutScreen
import com.yonder.addtolist.scenes.about.LicenceScreen
import com.yonder.addtolist.scenes.accountdetail.AccountScreen
import com.yonder.addtolist.scenes.createlist.CreateNewList
import com.yonder.addtolist.scenes.home.ListScreen
import com.yonder.addtolist.scenes.languageselection.LanguageScreen
import com.yonder.addtolist.scenes.listdetail.ListDetailScreen
import com.yonder.addtolist.scenes.login.LoginScreen
import com.yonder.addtolist.scenes.premium.PremiumScreen
import com.yonder.addtolist.scenes.productdetail.ProductDetailScreen
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
                        Route.SPLASH.key, Route.LOGIN.key -> {
                            topBarState.value = false
                            bottomBarState.value = false
                        }
                        Route.LIST.key, Route.SETTINGS.key-> {
                            backArrowState.value = false
                            topBarState.value = true
                            bottomBarState.value = true
                        }
                        Route.PREMIUM.key -> {
                            bottomBarState.value = false
                            topBarState.value = true
                            backArrowState.value = true
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
                                    title = {
                                        Text(
                                            text =
                                            stringResource(id = Route.find(navController.currentDestination?.route.orEmpty()).value)
                                        )
                                    },
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
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Splash.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Login.route) { LoginScreen(navController) }
                            composable(Screen.Splash.route) { SplashScreen(navController) }
                            composable(Screen.List.route) { ListScreen(navController) }
                            composable(Screen.Settings.route) { Settings(navController) }
                            composable(Screen.CreateNewList.route) { CreateNewList(navController) }
                            composable(Screen.Language.route) { LanguageScreen(navController) }
                            composable(Screen.About.route) { AboutScreen(navController) }
                            composable(Screen.Account.route) { AccountScreen() }
                            composable(Screen.License.route) { LicenceScreen() }
                            composable(Screen.Premium.route) { PremiumScreen(navController) }
                            composable(Screen.ListDetail.route) { ListDetailScreen(navController) }
                            composable(Screen.ProductDetail.route) {
                                ProductDetailScreen(
                                    navController
                                )
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

    object Login : Screen(Route.LOGIN.key, R.string.title_login, Icons.Filled.List)
    object Splash : Screen(Route.SPLASH.key, R.string.title_splash, Icons.Filled.List)
    object List : Screen(Route.LIST.key, R.string.title_list, Icons.Filled.List)
    object Settings : Screen(Route.SETTINGS.key, R.string.title_settings, Icons.Filled.Settings)
    object CreateNewList :
        Screen(Route.CREATE_NEW_LIST.key, R.string.create_new_list, Icons.Filled.List)

    object About : Screen(Route.ABOUT.key, R.string.title_about, Icons.Filled.Settings)
    object Account : Screen(Route.ACCOUNT.key, R.string.title_account_detail, Icons.Filled.Settings)
    object Language :
        Screen(Route.LANGUAGE.key, R.string.title_language_selection, Icons.Filled.Settings)

    object ListDetail : Screen(Route.LIST_DETAIL.key, R.string.title_list_detail, Icons.Filled.Settings)
    object ProductDetail : Screen(Route.PRODUCT_DETAIL.key, R.string.product_details, Icons.Filled.List)
    object License : Screen(Route.LICENSE.key, R.string.title_license, Icons.Filled.Settings)
    object Premium : Screen(Route.PREMIUM.key, R.string.title_premium, Icons.Filled.Settings)

}


enum class Route(var key: String, @StringRes var value: Int) {
    CREATE_NEW_LIST("createNewList", R.string.create_new_list),
    SETTINGS("settings", R.string.title_settings),
    LIST("list", R.string.title_shopping_lists),
    LIST_DETAIL("list_detail", R.string.title_list_detail),
    ABOUT("about", R.string.title_about),
    ACCOUNT("account", R.string.title_account_detail),
    LANGUAGE("language", R.string.title_language_selection),
    SPLASH("splash", R.string.title_splash),
    LOGIN("login", R.string.title_login),
    PRODUCT_DETAIL("product_detail", R.string.product_details),
    LICENSE("license", R.string.license),
    PREMIUM("premium", R.string.title_premium);

    companion object {
        fun find(key: String) = values().find { it.key == key } ?: SPLASH
    }

}



