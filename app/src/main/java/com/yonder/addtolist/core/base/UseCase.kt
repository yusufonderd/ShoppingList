package com.yonder.addtolist.core.base

import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 17.11.2021
 */

interface UseCase<in Input, out Output> {
  suspend operator fun invoke(input: Input): Flow<Output>
}

interface NoInputUseCase<out Output> {
  suspend operator fun invoke(): Flow<Output>
}


interface UseCaseNonFlow<in Input, out Output> {
  suspend operator fun invoke(input: Input): Output
}