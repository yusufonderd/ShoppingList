package com.yonder.addtolist.scenes.productdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.common.enums.ProductUnitType
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.domain.uimodel.CategoryUiModel
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.domain.usecase.GetProductDetail
import com.yonder.addtolist.domain.usecase.GetUserList
import com.yonder.addtolist.domain.usecase.UpdateProductOfUserList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
@Suppress("TooManyFunctions")
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetail: GetProductDetail,
    private val updateProductUseCase: UpdateProductOfUserList,
    private val getUserList: GetUserList
) : ViewModel() {

    var note by mutableStateOf(EMPTY_STRING)
    var name by mutableStateOf(EMPTY_STRING)
    var price by mutableStateOf(EMPTY_STRING)
    var categoryName by mutableStateOf(EMPTY_STRING)
    var selectedCategory: CategoryUiModel? = null

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun fetchList(uuid: String) {
        viewModelScope.launch {
            getUserList.invoke(uuid).collectLatest { userListUIModel ->
                _uiState.update {
                    it.copy(userList = userListUIModel)
                }
            }
        }
    }

    fun fetchProduct(productId: Int) {
        viewModelScope.launch {
            val params = getProductDetail(productId)
            params
                .mapNotNull { it }
                .collectLatest { param ->
                    val product = param.product
                    note = product.note
                    name = product.name
                    price = product.price
                    categoryName = param.categoryOfProduct?.formattedName.orEmpty()
                    selectedCategory = param.categoryOfProduct
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            categories = param.categories,
                            product = product,
                            categoryOfProduct = param.categoryOfProduct
                        )
                    }
                }
        }
    }

    fun onChangeNote(note: String) {
        this.note = note
    }

    fun onChangePrice(price: String) {
        this.price = price
    }

    fun onChangeName(name: String) {
        this.name = name
    }

    fun onChangeCategory(product: UserListProductUiModel,category: CategoryUiModel) {
        this.categoryName = category.formattedName
        selectedCategory = category
        product.categoryName = category.name
        product.categoryImage = category.image
        update(product)
    }

    fun toggleFavorite(product: UserListProductUiModel) {
        product.isFavorite = !product.isFavorite
        update(product)
    }

    fun increaseQuantity(product: UserListProductUiModel) {
        product.quantityValue = product.quantityValue.plus(1.0)
        update(product)
    }

    fun decreaseQuantity(product: UserListProductUiModel) {
        if (product.quantityValue > 1.0){
            product.quantityValue = product.quantityValue.minus(1.0)
            update(product)
        }
    }

    fun updateUnit(product: UserListProductUiModel, unit: ProductUnitType) {
        if (product.unit != unit.value) {
            product.unit = unit.value
            update(product)
        }
    }

    private fun update(product: UserListProductUiModel, productName: String = product.name) {
        viewModelScope.launch {
            updateProductUseCase.invoke(
                productName = productName,
                listUUID = product.listUUID,
                product = product
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val categories: List<CategoryUiModel> = emptyList(),
        val product: UserListProductUiModel? = null,
        var userList: UserListUiModel? = null,
        val categoryOfProduct: CategoryUiModel? = null
    )


}

