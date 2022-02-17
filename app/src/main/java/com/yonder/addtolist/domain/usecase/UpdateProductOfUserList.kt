package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.extensions.toInt
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.network.ApiService
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.uimodel.UserListProductMapper
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

class UpdateProductOfUserList @Inject constructor(
    private val appDatabase: AppDatabase,
    private val api: ApiService
) {
    suspend operator fun invoke(
        productName: String,
        listId: Int,
        listUUID: String,
        product: UserListProductUiModel
    ) {
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
                    done = product.isDone.toInt()
                    favorite = product.isFavorite.toInt()
                }
        appDatabase.userListProductDao().update(productEntity)
        val request =
            UserListProductMapper.mapEntityToResponse(listId = "$listId", input = productEntity)
        try {
            val response = api.updateProduct(product.id, request)
            if (response.success == true) {
                productEntity.sync = true
                appDatabase.userListProductDao().update(productEntity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

}