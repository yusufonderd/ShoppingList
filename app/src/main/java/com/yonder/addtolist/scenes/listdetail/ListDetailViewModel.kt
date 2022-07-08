package com.yonder.addtolist.scenes.listdetail

import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.domain.mapper.ItemUiModelMapper
import com.yonder.addtolist.domain.uimodel.ItemUiModel
import com.yonder.addtolist.domain.uimodel.ProductEntityUiModel
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.domain.usecase.*
import com.yonder.addtolist.local.entity.CATEGORY_OTHER_IMAGE
import com.yonder.addtolist.scenes.listdetail.domain.AddProductUseCase
import com.yonder.core.base.BaseViewModel
import com.yonder.core.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

private const val QUERY_LIMIT = 10

@HiltViewModel
class ListDetailViewModel @Inject constructor(
    private val getPopularProducts: GetPopularProducts,
    private val getProductByQuery: GetProductByQuery,
    private val productUseCase: ProductUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductOfUserList,
    private val deleteProductOfUserListUseCase: DeleteProductOfUserList,
    private val getUserListUseCase: GetUserList,
    private val userPreferenceDataStore: UserPreferenceDataStore
) : BaseViewModel<ListDetailViewModel.UiEvent>() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    var job: Job? = null

    fun setSelectedList(uuid: String){
        userPreferenceDataStore.setSelectedListUUID(uuid)
    }
    fun fetchProducts(listUUID: String, query: String = EMPTY_STRING) {
        val flow1 = getUserListUseCase(listUUID)
        val flow2 = if (query.trim().isEmpty()) {
            getPopularProducts()
        } else {
            getProductByQuery(
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

    fun addProduct(userListUUID: String, productName: String) {
            viewModelScope.launch {
                productUseCase.getProductEntityForName(productName).collect { productEntity ->
                    val categoryImage = productEntity?.categoryImage ?: CATEGORY_OTHER_IMAGE
                    addProductUseCase.invoke(
                        listUUID = userListUUID,
                        productName = productName,
                        productCategoryImage = categoryImage
                    ).collect()
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

