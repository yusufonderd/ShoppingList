package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.domain.product.ProductRepository
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

class DeleteProductUseCaseImpl @Inject constructor(
   private val productRepository: ProductRepository
) :
  DeleteProductUseCase {
  override suspend fun invoke(product: UserListProductUiModel)  = productRepository.delete(product)
}

interface DeleteProductUseCase {
  suspend operator fun  invoke(product: UserListProductUiModel)
}

