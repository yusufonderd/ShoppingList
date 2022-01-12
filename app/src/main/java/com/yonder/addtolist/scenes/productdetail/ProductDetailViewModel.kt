package com.yonder.addtolist.scenes.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.data.SingleLiveEvent
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.domain.usecase.DeleteUserListProduct
import com.yonder.addtolist.domain.usecase.GetProductDetail
import com.yonder.addtolist.domain.usecase.UpdateUserListProduct
import com.yonder.addtolist.scenes.home.domain.model.CategoryUiModel
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.productdetail.model.enums.ProductUnitType
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
    private val deleteProductUseCase: DeleteUserListProduct,
    private val updateProductUseCase: UpdateUserListProduct,
) : ViewModel() {

    private val _event: SingleLiveEvent<ProductDetailViewEvent> =
        SingleLiveEvent()
    val event: SingleLiveEvent<ProductDetailViewEvent> get() = _event

    fun fetchProductId(selectedProductId: Int) {
        viewModelScope.launch {
            val params = getProductDetail.invoke(selectedProductId)
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

    fun toggleDone(product: UserListProductUiModel) {
        product.isDone = !product.isDone
        update(product)
    }

    fun delete(product: UserListProductUiModel) {
        viewModelScope.launch {
            deleteProductUseCase.invoke(product)
        }
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

    fun updateProductName(product: UserListProductUiModel, name: String) {
        val currentProductName = product.name
        if (currentProductName != name) {
            product.name = name
            update(product = product, productName = currentProductName)
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

    fun updateProductPrice(product: UserListProductUiModel, price: Double?) {
        if (product.priceValue != price) {
            product.priceValue = price.orZero()
            update(product)
        }
    }

    fun updateProductNote(product: UserListProductUiModel, note: String?) {
        if (product.note != note) {
            product.note = note.orEmpty()
            update(product)
        }
    }

    fun updateCategory(product: UserListProductUiModel, categoryPosition: Int) {
        val selectedCategories = getCategories()?.getOrNull(categoryPosition)
        if (selectedCategories != null && selectedCategories.image != product.categoryImage) {
            product.categoryImage = selectedCategories.image
            product.categoryName = selectedCategories.name
            update(product)
        }
    }

    private fun getCategories(): List<CategoryUiModel>? {
        val viewState = event.value
        if (viewState is ProductDetailViewEvent.Load) {
            return viewState.categories
        }
        return null
    }

}

