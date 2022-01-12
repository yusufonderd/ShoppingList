package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.domain.product.ProductRepository
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

class DeleteUserListProduct @Inject constructor(
    private val productRepository: ProductRepository
) {
     suspend fun invoke(product: UserListProductUiModel){
        return  productRepository.delete(product)
    }
}