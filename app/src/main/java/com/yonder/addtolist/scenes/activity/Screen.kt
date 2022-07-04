package com.yonder.addtolist.scenes.activity

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.yonder.addtolist.R

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
    object List : Screen(Route.LIST.key, R.string.title_shopping_lists, Icons.Filled.List)
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





