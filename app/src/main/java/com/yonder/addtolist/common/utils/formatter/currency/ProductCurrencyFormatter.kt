package com.yonder.addtolist.common.utils.formatter.currency

import java.text.NumberFormat
import java.util.*

/**
 * @author yusuf.onder
 * Created on 22.08.2021
 */
class ProductCurrencyFormatter : Formatter<Double, String> {

  override fun format(value: Double, currencyCode: String): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(currencyCode)
    return format.format(value)
  }

}