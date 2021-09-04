package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.domain.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

class DeleteProductUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase,
   private val productRepository: ProductRepository
) :
  DeleteProductUseCase {
  override  fun delete(product: UserListProductUiModel) = flow {
    val productEntity = appDatabase.userListProductDao()
      .findByListUUID(listUUID = product.listUUID, productName = product.name)
    productRepository.removeProduct(productEntity)
    emit(productEntity)
    /* appDatabase.userListProductDao().delete(productEntity)
     emit(productEntity)*/
  }.catch { e ->
    e.printStackTrace()
  }
}

interface DeleteProductUseCase {
    fun delete(product: UserListProductUiModel): Flow<UserListProductEntity>
}


