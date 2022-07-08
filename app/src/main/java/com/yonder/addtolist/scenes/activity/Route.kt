package com.yonder.addtolist.scenes.activity

import androidx.annotation.StringRes
import com.yonder.addtolist.R

enum class Route(var key: String, @StringRes var value: Int) {
    CREATE_NEW_LIST("createNewList", R.string.create_new_list),
    SETTINGS("settings", R.string.title_settings),
    LISTS("lists", R.string.title_shopping_lists),
    LIST_DETAIL("list_detail/uuid={uuid}", R.string.title_list_detail),
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
