package com.yonder.addtolist.scenes.listdetail

import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.domain.mapper.ItemUiModelMapper
import com.yonder.addtolist.domain.uimodel.ProductEntityUiModel
import com.yonder.addtolist.domain.usecase.DeleteProductOfUserList
import com.yonder.addtolist.domain.usecase.GetUserList
import com.yonder.addtolist.domain.usecase.UpdateProductOfUserList
import com.yonder.addtolist.local.entity.CATEGORY_OTHER_IMAGE
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.scenes.listdetail.domain.category.ProductQueryUseCase
import com.yonder.addtolist.domain.usecase.ProductUseCase
import com.yonder.addtolist.scenes.listdetail.domain.AddProductUseCase
import com.yonder.addtolist.scenes.listdetail.items.model.ItemUiModel
import com.yonder.core.base.BaseViewModel
import com.yonder.core.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

private const val QUERY_LIMIT = 10

@HiltViewModel
class ListDetailViewModel @Inject constructor(
    private val productQueryUseCase: ProductQueryUseCase,
    private val productUseCase: ProductUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductOfUserList,
    private val deleteProductOfUserListUseCase: DeleteProductOfUserList,
    private val getUserListUseCase: GetUserList
) : BaseViewModel<ListDetailViewModel.UiEvent>() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    var job: Job? = null

    var listId: Int = 0

    fun fetchProducts(listUUID: String, query: String = EMPTY_STRING) {
        val flow1 = getUserListUseCase.invoke(listUUID)
        val flow2 = if (query.trim().isEmpty()) {
            productQueryUseCase.fetchPopularProducts()
        } else {
            productQueryUseCase.fetchProductByQuery(
                query = query,
                limit = QUERY_LIMIT
            )
        }
        job?.cancel()
        job = viewModelScope.launch {
            flow1.combine(flow2) { userList, listingProducts ->
                val items = ItemUiModelMapper.mapToUiModel(
                    addedProducts = userList.products,
                    filteredProducts = listingProducts
                )
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        userList = userList,
                        products = listingProducts,
                        userListProducts = userList.products,
                        items = items
                    )
                }
                pushEvent(UiEvent.ShowKeyboard)
            }.collect()
        }

    }

    fun addProduct(listId: Int?, userListUUID: String, productName: String) {
        listId?.let {
            viewModelScope.launch {
                productUseCase.getProductEntityForName(productName).collect { productEntity ->
                    val categoryImage = productEntity?.categoryImage ?: CATEGORY_OTHER_IMAGE
                    addProductUseCase.invoke(
                        listId = listId.toString(),
                        listUUID = userListUUID,
                        productName = productName,
                        productCategoryImage = categoryImage
                    ).collect()
                }
            }
        }
    }


    fun increaseQuantity(product: UserListProductUiModel) {
        product.quantityValue = product.quantityValue.plus(1.0)
        update(product)
    }

    fun decreaseQuantity(product: UserListProductUiModel) {
        if (product.quantityValue <= 1.0){
            deleteProduct(product = product)
            return
        }
        product.quantityValue = product.quantityValue.minus(1.0)
        update(product)
    }

    fun toggleDone(product: UserListProductUiModel) {
        product.isDone = !product.isDone
        update(product)
    }

    fun removeFromFavorites(product: UserListProductUiModel) {
        product.isFavorite = false
        update(product)
    }

    private fun update(product: UserListProductUiModel, productName: String = product.name) {
        viewModelScope.launch {
            updateProductUseCase(
                productName = productName,
                listId = listId,
                listUUID = product.listUUID,
                product = product
            )
        }
    }

    fun deleteProduct(product: UserListProductUiModel) {
        viewModelScope.launch {
            deleteProductOfUserListUseCase.invoke(product)
        }
    }



    data class UiState(
        val isLoading: Boolean = false,
        val userList: UserListUiModel? = null,
        val userListProducts: List<UserListProductUiModel> = emptyList(),
        val products: List<ProductEntityUiModel> = emptyList(),
        val items: List<ItemUiModel> = emptyList()
    )

    sealed class UiEvent : Event {
        object Initial : UiEvent()
        object ShowKeyboard : UiEvent()
    }

}

