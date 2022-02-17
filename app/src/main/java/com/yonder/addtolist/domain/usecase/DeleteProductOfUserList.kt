package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.data.repository.ProductRepository
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

class DeleteProductOfUserList @Inject constructor(
    private val productRepository: ProductRepository
) {
     suspend fun invoke(product: UserListProductUiModel){
        return  productRepository.delete(product)
    }
}