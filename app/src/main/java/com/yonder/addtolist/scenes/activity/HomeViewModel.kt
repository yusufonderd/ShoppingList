package com.yonder.addtolist.scenes.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.core.extensions.toSafeDouble
import com.yonder.addtolist.data.datasource.CategoryDataSource
import com.yonder.addtolist.data.datasource.UserListDataSource
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.usecase.DeleteProductOfUserList
import com.yonder.addtolist.domain.usecase.UpdateProductOfUserList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateProductUseCase: UpdateProductOfUserList,
    private val deleteProductOfUserListUseCase: DeleteProductOfUserList,
    private val userPreferenceDataStore: UserPreferenceDataStore,
    private val userListDataSource: UserListDataSource,
    private val categoryDataSource: CategoryDataSource

) : ViewModel() {

    var selectedListName = MutableStateFlow("")

    fun updateProduct(
        product: UserListProductUiModel,
        name: String,
        categoryImage: String,
        categoryName: String,
        price: String,
        quantity: String,
        note: String
    ) {


        val updatedProduct = product.copy(
            name = name,
            priceValue = price.toSafeDouble(),
            price = price,
            categoryImage = categoryImage,
            categoryName = categoryName,
            quantityValue = quantity.toSafeDouble(),
            note = note
        )
        if (updatedProduct != product) {
            viewModelScope.launch {
                updateProductUseCase.invoke(
                    productName = product.name,
                    listUUID = product.listUUID,
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

    fun deleteSelectedList() {
        val listUUID = userPreferenceDataStore.getSelectedListUUID()
        viewModelScope.launch(Dispatchers.IO) {
            val list = userListDataSource.getUserList(listUUID.orEmpty())
            if (list != null) {
                userListDataSource.delete(list)
            }
        }
    }

    fun deletePurchasedItems() {
        val listUUID = userPreferenceDataStore.getSelectedListUUID()
        viewModelScope.launch(Dispatchers.IO) {
            val userList = userListDataSource.getFirstUserListWithProducts(listUUID.orEmpty())
            userList?.products?.filter { it.isPurchased() }?.forEach {
                categoryDataSource.delete(it)
            }
        }
    }

    fun getSelectedList(listUUID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = userListDataSource.getUserList(listUUID)
            selectedListName.value = list?.name.orEmpty()
        }
    }

    fun getLocale(): Locale {
        return userPreferenceDataStore.getLocale()
    }

}
