package com.yonder.addtolist.core.base

/**
 * Yusuf Onder on 09,May,2021
 */

interface BaseMapper <in T , out R> {
  fun map(input : T) : R
}
