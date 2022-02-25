package com.yonder.addtolist.common.utils

import android.content.Context
import android.os.LocaleList
import com.yonder.addtolist.common.utils.device.BuildUtils.isAtLeast17Api
import com.yonder.addtolist.common.utils.device.BuildUtils.isAtLeast24Api
import java.util.*

/**
 * @author yusuf.onder
 * Created on 9.02.2022
 */
class ContextWrapper(base: Context?) : android.content.ContextWrapper(base) {
    companion object {
        fun wrap(context: Context, newLocale: Locale?): ContextWrapper {
            var context = context
            val res = context.resources
            val configuration = res.configuration
            when {
                isAtLeast24Api -> {
                    configuration.setLocale(newLocale)
                    val localeList = LocaleList(newLocale)
                    LocaleList.setDefault(localeList)
                    configuration.setLocales(localeList)
                    context = context.createConfigurationContext(configuration)
                }
                isAtLeast17Api -> {
                    configuration.setLocale(newLocale)
                    context = context.createConfigurationContext(configuration)
                }
                else -> {
                    configuration.locale = newLocale
                    res.updateConfiguration(configuration, res.displayMetrics)
                }
            }
            return ContextWrapper(context)
        }
    }
}