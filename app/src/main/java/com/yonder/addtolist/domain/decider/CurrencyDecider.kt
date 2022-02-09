package com.yonder.addtolist.domain.decider

import android.content.Context
import com.yonder.addtolist.R
import com.yonder.addtolist.common.utils.formatter.currency.CurrencyProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 9.02.2022
 */


class CurrencyDecider @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun formatPrice(
        price: Double?,
        currency: String = CurrencyProvider.DEFAULT_CURRENCY_SIGN
    ): String {
        val originalPrice = price ?: return ""
        return context.getString(
            R.string.format_price,
            originalPrice,
            currency
        )
    }
}
