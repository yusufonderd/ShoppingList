package com.yonder.addtolist.scenes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.common.ui.extensions.openWithKeyboard
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.local.entity.CATEGORY_OTHER_IMAGE
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.detail.domain.category.ProductQueryUseCase
import com.yonder.addtolist.scenes.detail.domain.product.ProductUseCase
import com.yonder.addtolist.scenes.list.domain.usecase.LocalListUseCase
import com.yonder.addtolist.scenes.productdetail.domain.usecase.LocalProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

private const val QUERY_LIMIT = 10

private const val DONE_VALUE = 1
private const val NO_DONE_VALUE = 0
@HiltViewModel
class ListDetailViewModel @Inject constructor(
  private val productQueryUseCase: ProductQueryUseCase,
  private val productUseCase: ProductUseCase,
  private val localProductUseCase: LocalProductUseCase,
  private val localUserListUseCase: LocalListUseCase
) : ViewModel() {

  private val _state: MutableStateFlow<ListDetailViewState> =
    MutableStateFlow(ListDetailViewState.Initial)
  val state: StateFlow<ListDetailViewState> get() = _state
  var job: Job? = null


  fun fetchProducts(listUUID: String, query: String = EMPTY_STRING) {
    val flow1 = localUserListUseCase.getUserListByUUID(listUUID)
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
      flow1.combine(flow2) { userListWithProducts, listingProducts ->
        if (userListWithProducts.products.isEmpty()){
          _state.value = ListDetailViewState.OpenKeyboard
        }
        _state.value = ListDetailViewState.UserListContent(
          userListWithProducts,
          listingProducts,
          query
        )
      }.collect()
    }

  }

  /*private fun fetchCategories() {
    categoryListUseCase.getCategories()
      .onEach { result ->
        result.onSuccess {
          _state.value = ListDetailViewState.ShowContent(it)
        }.onLoading {
          _state.value = ListDetailViewState.Loading
        }.onError { error ->
          _state.value = ListDetailViewState.Error(error.toReadableMessage())
        }
      }.launchIn(viewModelScope)
  }*/


  fun addProduct(listId: String, userListUUID: String, productName: String) = with(viewModelScope) {
    launch {
      productUseCase.getProductEntityForName(productName).collect { productEntity ->
        val categoryImage = productEntity?.categoryImage ?: CATEGORY_OTHER_IMAGE
        productUseCase.addProduct(
          listId = listId,
          listUUID = userListUUID,
          productName = productName,
          productCategoryImage = categoryImage
        ).collect()
      }
    }

  }


  fun increaseQuantity(listId: String, product: UserListProductEntity) = with(viewModelScope) {
    product.quantity = product.quantity?.plus(1.0)
    launch {
      productUseCase.updateProduct(listId, product)
        .collect()
    }
  }

  fun decreaseQuantity(listId: String, product: UserListProductEntity) = with(viewModelScope) {
    product.quantity = product.quantity?.minus(1.0)
    launch {
      productUseCase.updateProduct(listId, product)
        .collect()
    }
  }

  fun toggleDone(product: UserListProductEntity) {
    if (product.wrappedDone()) {
      product.done = NO_DONE_VALUE
    } else {
      product.done = DONE_VALUE
    }
    viewModelScope.launch {
      localProductUseCase.update(product)
    }
  }



  fun removeProduct(product: UserListProductEntity) = with(viewModelScope) {
    launch {
      productUseCase.removeProduct(product).collect()
    }
  }
}

