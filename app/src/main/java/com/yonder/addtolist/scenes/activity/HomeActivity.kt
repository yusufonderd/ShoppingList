package com.yonder.addtolist.scenes.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yonder.addtolist.scenes.about.AboutScreen
import com.yonder.addtolist.scenes.about.LicenceScreen
import com.yonder.addtolist.scenes.createlist.CreateNewList
import com.yonder.addtolist.scenes.home.ListScreen
import com.yonder.addtolist.scenes.languageselection.LanguageScreen
import com.yonder.addtolist.scenes.listdetail.ListDetailScreen
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
                    bottomBarState.value = false
                    when (navBackStackEntry?.destination?.route) {
                        Route.SPLASH.key, Route.LOGIN.key -> {
                            topBarState.value = false
                            //  bottomBarState.value = false
                        }
                        Route.LIST.key, Route.SETTINGS.key -> {
                            backArrowState.value = false
                            topBarState.value = true
                            //   bottomBarState.value = true
                        }
                        Route.PREMIUM.key -> {
                            topBarState.value = true
                            backArrowState.value = true
                            //  bottomBarState.value = false
                        }
                        else -> {
                            topBarState.value = true
                            backArrowState.value = true
                            //  bottomBarState.value = true
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
                            composable(Screen.Splash.route) { SplashScreen(navController) }
                            composable(Screen.List.route) { ListScreen(navController) }
                            composable(Screen.Settings.route) { Settings(navController) }
                            composable(Screen.CreateNewList.route) { CreateNewList(navController) }
                            composable(Screen.Language.route) { LanguageScreen(navController) }
                            composable(Screen.About.route) { AboutScreen(navController) }
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

