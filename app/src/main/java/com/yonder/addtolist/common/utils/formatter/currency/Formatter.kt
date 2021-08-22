package com.yonder.addtolist.common.utils.formatter.currency

/**
 * @author yusuf.onder
 * Created on 22.08.2021
 */
interface Formatter<T, R> {
  fun format(value: T, currencyCode: String = "EUR"): R
}
