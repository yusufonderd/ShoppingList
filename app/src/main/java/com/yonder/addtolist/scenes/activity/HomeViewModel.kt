package com.yonder.addtolist.scenes.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.usecase.DeleteProductOfUserList
import com.yonder.addtolist.domain.usecase.UpdateProductOfUserList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateProductUseCase: UpdateProductOfUserList,
    private val deleteProductOfUserListUseCase: DeleteProductOfUserList
    ) : ViewModel(){

    fun updateProduct(
        product: UserListProductUiModel,
        listId: Int,
        name: String,
        categoryImage: String,
        categoryName: String,
        price: String,
        note: String,

    ) {

        val priceDouble = price
            .replace(",",".")
            .toDoubleOrNull().orZero()
        val updatedProduct = product.copy(
            name = name,
            priceValue = priceDouble,
            price = price,
            categoryImage = categoryImage,
            categoryName = categoryName,
            note = note
        )
        if (updatedProduct != product) {
             viewModelScope.launch {
                 updateProductUseCase.invoke(
                     productName = product.name,
                     listUUID = product.listUUID,
                     listId = listId,
                     product = updatedProduct
                 )
             }
        }
    }

    fun delete(product: UserListProductUiModel) {
        viewModelScope.launch {
            deleteProductOfUserListUseCase.invoke(product)
        }
    }




}
