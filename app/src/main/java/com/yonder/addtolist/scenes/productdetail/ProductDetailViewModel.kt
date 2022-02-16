package com.yonder.addtolist.scenes.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.data.SingleLiveEvent
import com.yonder.addtolist.domain.usecase.DeleteUserListProduct
import com.yonder.addtolist.domain.usecase.GetProductDetail
import com.yonder.addtolist.domain.usecase.UpdateUserListProduct
import com.yonder.addtolist.domain.uimodel.CategoryUiModel
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.common.enums.ProductUnitType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
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
    private val updateProductUseCase: UpdateUserListProduct,
) : ViewModel() {

    var listId: Int = 0

    private val _event: SingleLiveEvent<ProductDetailViewEvent> =
        SingleLiveEvent()
    val event: SingleLiveEvent<ProductDetailViewEvent> get() = _event

    fun fetchProduct(productId: Int) {
        viewModelScope.launch {
            val params = getProductDetail(productId)
            params
                .mapNotNull { it }
                .collectLatest { param ->
                    _event.value = ProductDetailViewEvent.Load(
                        categories = param.categories,
                        product = param.product,
                        categoryOfProduct = param.categoryOfProduct
                    )
                }
        }
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
        product.quantityValue = product.quantityValue.minus(1.0)
        update(product)
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
                listId = listId,
                product = product
            )
        }
    }

    private fun getCategories(): List<CategoryUiModel>? {
        val viewState = event.value
        if (viewState is ProductDetailViewEvent.Load) {
            return viewState.categories
        }
        return null
    }

    fun getCategoryBy(categoryName: String): CategoryUiModel? {
        return getCategories()?.find { categoryName == it.formattedName }
    }

}

