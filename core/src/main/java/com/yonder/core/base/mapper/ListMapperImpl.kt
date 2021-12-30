package com.yonder.core.base.mapper

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */

class ListMapperImpl<I, O>(
  private val mapper: Mapper<I, O>
) : ListMapper<I, O> {
  override fun map(input: List<I>): List<O> {
    return input.map { mapper.map(it) }
  }
}
