package com.yonder.addtolist.scenes.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.usecase.DeleteUserListProduct
import com.yonder.addtolist.domain.usecase.UpdateUserListProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateProductUseCase: UpdateUserListProduct,
    private val deleteProductUseCase: DeleteUserListProduct
    ) : ViewModel(){

    fun updateProduct(
        product: UserListProductUiModel,
        listId: Int,
        name: String,
        categoryImage: String,
        categoryName: String,
        price: Double,
        note: String,

    ) {
        val updatedProduct = product.copy(
            name = name,
            priceValue = price,
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
            deleteProductUseCase.invoke(product)
        }
    }




}
