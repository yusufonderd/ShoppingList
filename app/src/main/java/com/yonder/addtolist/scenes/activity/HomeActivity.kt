package com.yonder.addtolist.scenes.activity

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yonder.addtolist.scenes.about.AboutScreen
import com.yonder.addtolist.scenes.about.LicenceScreen
import com.yonder.addtolist.scenes.createlist.CreateNewList
import com.yonder.addtolist.scenes.lists.ListScreen
import com.yonder.addtolist.scenes.languageselection.LanguageScreen
import com.yonder.addtolist.scenes.listdetail.ListDetailScreen
import com.yonder.addtolist.scenes.premium.PremiumScreen
import com.yonder.addtolist.scenes.productdetail.ProductDetailScreen
import com.yonder.addtolist.scenes.settings.Settings
import com.yonder.addtolist.scenes.splash.SplashScreen
import com.yonder.addtolist.theme.BreakingBadTheme
import dagger.hilt.android.AndroidEntryPoint
import com.yonder.addtolist.R
import com.yonder.addtolist.uicomponent.TextIcon
import timber.log.Timber


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            BreakingBadTheme {
                val context = LocalContext.current

                val homeViewModel = hiltViewModel<HomeViewModel>()

                val config: Configuration =
                    context.resources.configuration
                config.locale = homeViewModel.getLocale()
                context.resources.updateConfiguration(
                    config,
                    context.resources.displayMetrics
                )

                Surface(color = MaterialTheme.colors.background) {

                    var showMenu by remember { mutableStateOf(false) }

                    val navController = rememberNavController()
                    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
                    val topBarState = rememberSaveable { (mutableStateOf(true)) }
                    val backArrowState = rememberSaveable { (mutableStateOf(false)) }

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    when (navBackStackEntry?.destination?.route) {
                        Route.SPLASH.key -> {
                            topBarState.value = false
                            bottomBarState.value = false
                        }
                        Route.LISTS.key, Route.SETTINGS.key -> {
                            backArrowState.value = false
                            topBarState.value = true
                            bottomBarState.value = true
                        }
                        Route.PREMIUM.key -> {
                            topBarState.value = true
                            backArrowState.value = true
                            bottomBarState.value = false
                        }
                        Route.PRODUCT_DETAIL.key, Route.CREATE_NEW_LIST.key -> {
                            topBarState.value = true
                            backArrowState.value = true
                            bottomBarState.value = false
                        }
                        else -> {
                            topBarState.value = true
                            backArrowState.value = true
                            bottomBarState.value = true
                        }
                    }
                    val activity = (LocalContext.current as? Activity)

                    Scaffold(
                        topBar = {
                            if (topBarState.value) {
                                TopAppBar(
                                    backgroundColor = colorResource(id = R.color.white),
                                    title = {
                                        when (navBackStackEntry?.destination?.route) {
                                            Route.LIST_DETAIL.key -> {
                                                Text(
                                                    color = colorResource(id = R.color.black),
                                                    text =
                                                    homeViewModel.selectedListName.collectAsState().value
                                                )
                                            }
                                            else -> {
                                                Text(
                                                    color = colorResource(id = R.color.black),
                                                    text =
                                                    stringResource(id = Route.find(navController.currentDestination?.route.orEmpty()).value)
                                                )
                                            }
                                        }
                                    },
                                    actions =
                                    {
                                        when (navBackStackEntry?.destination?.route) {
                                            Route.LIST_DETAIL.key -> {
                                                IconButton(onClick = {
                                                    showMenu = !showMenu
                                                }) {
                                                    Icon(
                                                        Icons.Default.MoreVert,
                                                        stringResource(id = R.string.more)
                                                    )
                                                }
                                            }
                                        }
                                        DropdownMenu(
                                            expanded = showMenu,
                                            onDismissRequest = { showMenu = false }
                                        ) {
                                            DropdownMenuItem(onClick = {
                                                showMenu = false
                                                homeViewModel.deleteSelectedList()
                                                navController.popBackStack()
                                            }) {
                                                TextIcon(
                                                    Icons.Filled.Delete,
                                                    stringResource(id = R.string.delete_list),
                                                    colorResource(id = R.color.colorRed)
                                                )
                                            }
                                        }
                                    },
                                    navigationIcon = if (backArrowState.value) {
                                        {
                                            IconButton(onClick = { navController.navigateUp() }) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowBack,
                                                    contentDescription = "Back",
                                                    tint = colorResource(id = R.color.black)
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
                                            alwaysShowLabel = true,
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
                        BackHandler {
                            val currentRoute =
                                navController.currentBackStackEntry?.destination?.route
                            if (currentRoute == Route.LISTS.key){
                                activity?.finish()
                            }else{
                                navController.popBackStack()
                            }
                        }
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Splash.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {

                            composable(Screen.Splash.route) { SplashScreen(navController) }
                            composable(Screen.Lists.route) { ListScreen(navController) }
                            composable(Screen.ListDetail.route) { backStackEntry ->
                                val listUUID = backStackEntry.arguments?.getString("uuid")
                                ListDetailScreen(
                                    homeViewModel = homeViewModel,
                                    navController = navController,
                                    listUUID = listUUID.orEmpty()
                                )
                            }
                            composable(Screen.Settings.route) { Settings(navController) }
                            composable(Screen.CreateNewList.route) { CreateNewList(navController) }
                            composable(Screen.Language.route) { LanguageScreen(navController) }
                            composable(Screen.About.route) { AboutScreen(navController) }
                            composable(Screen.License.route) { LicenceScreen() }
                            composable(Screen.Premium.route) { PremiumScreen(navController) }
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

