package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.extensions.toInt
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.local.AppDatabase
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

class UpdateProductOfUserList @Inject constructor(
    private val appDatabase: AppDatabase
) {
    suspend operator fun invoke(
        productName: String,
        listUUID: String,
        product: UserListProductUiModel
    ) {
        try {
            val productEntity =
                appDatabase
                    .userListProductDao()
                    .findByListUUID(listUUID = listUUID, productName = productName)
                    .apply {
                        name = product.name
                        quantity = product.quantityValue
                        unit = product.unit
                        sync = false
                        note = product.note
                        price = product.priceValue
                        categoryImage = product.categoryImage
                        categoryName = product.categoryName
                        favorite = product.isFavorite.toInt()
                        done = product.isDone.toInt()
                    }
            appDatabase.userListProductDao().update(productEntity)
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

}