package com.yonder.addtolist.scenes.productdetail.domain

import com.yonder.addtolist.core.extensions.toInt
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 28.08.2021
 */

class UpdateProductUseCaseImpl @Inject constructor(private val appDatabase: AppDatabase) :
  UpdateProductUseCase {

  override suspend operator fun invoke(
    productName: String,
    listUUID: String,
    product: UserListProductUiModel
  ) {
    val productEntity =
      appDatabase
      .userListProductDao()
      .findByListUUID(listUUID = listUUID, productName = productName)
    productEntity.let {
      productEntity.name = product.name
      productEntity.quantity = product.quantityValue
      productEntity.unit = product.unit
      productEntity.note = product.note
      productEntity.price = product.priceValue
      productEntity.categoryImage = product.categoryImage
      productEntity.categoryName = product.categoryName
      productEntity.done = product.isDone.toInt()
      productEntity.favorite = product.isFavorite.toInt()
      appDatabase.userListProductDao().update(productEntity)
    }
  }

}

interface UpdateProductUseCase {
  suspend operator fun invoke(
    productName: String,
    listUUID: String,
    product: UserListProductUiModel
  )
}
