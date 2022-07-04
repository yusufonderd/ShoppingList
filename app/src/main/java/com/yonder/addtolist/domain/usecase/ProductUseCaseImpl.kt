package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.repository.ProductRepository
import com.yonder.addtolist.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
class ProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val dispatcher: CoroutineThread
) : ProductUseCase {

  override fun getProductEntityForName(name: String): Flow<ProductEntity?> {
    return productRepository.getProductEntityByName(name)
      .flowOn(dispatcher.io)
  }

}
