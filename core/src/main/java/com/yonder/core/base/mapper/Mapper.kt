package com.yonder.core.base.mapper

/**
 * Yusuf Onder on 09,May,2021
 */

interface Mapper <in T , out R> {
  fun map(input : T) : R
}

