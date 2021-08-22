package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 22.08.2021
 */

interface LocalProductUseCase {
  suspend fun delete(product: UserListProductEntity)
  suspend fun update(product: UserListProductEntity)
  fun getProductById(id: Int): Flow<UserListProductEntity>
  fun getCategories() : Flow<List<CategoryEntity>>
}