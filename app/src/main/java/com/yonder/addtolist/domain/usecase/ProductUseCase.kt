package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
interface ProductUseCase {
  fun getProductEntityForName(name: String) : Flow<ProductEntity?>
}
