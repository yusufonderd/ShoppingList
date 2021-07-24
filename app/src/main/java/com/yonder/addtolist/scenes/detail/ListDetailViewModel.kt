package com.yonder.addtolist.scenes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.detail.domain.category.CategoryListUseCase
import com.yonder.addtolist.scenes.detail.domain.product.ProductUseCase
import com.yonder.addtolist.scenes.list.domain.usecase.LocalListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow as Flow1

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */


@HiltViewModel
class ListDetailViewModel @Inject constructor(
  private val categoryListUseCase: CategoryListUseCase,
  private val productUseCase: ProductUseCase,
  private val localUserListUseCase: LocalListUseCase
) : ViewModel() {

  private val _state: MutableStateFlow<ListDetailViewState> =
    MutableStateFlow(ListDetailViewState.Initial)
  val state: StateFlow<ListDetailViewState> get() = _state

  val query = ""

  init {
    fetchCategories()
  }

  fun fetchProducts(listUUID: String, query: String = EMPTY_STRING) {
    val flow1 = localUserListUseCase.getUserListByUUID(listUUID)
    val flow2 = if (query.trim().isEmpty()) {
      categoryListUseCase.fetchPopularProducts()
    } else {
      categoryListUseCase.fetchProductByQuery(query)
    }
    viewModelScope.launch {
      flow1.combine(flow2) { userListWithProducts, listingProducts ->
        _state.value = ListDetailViewState.UserListContent(
          userListWithProducts,
          listingProducts,
          query
        )
      }.collect()
    }

  }

  private fun fetchCategories() {
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
  }





  fun addProduct(listId: String, userListUUID: String, productName: String) {
    productUseCase.addProduct(
      listId = listId,
      listUUID = userListUUID,
      productName = productName
    ).onEach {

    }.launchIn(viewModelScope)
  }

  fun increaseQuantity(listId: String, product: UserListProductEntity) {
    product.quantity = product.quantity?.plus(1.0)
    productUseCase.updateProduct(listId, product).onEach {
      Timber.d("updateProduct => $it")
    }.launchIn(viewModelScope)
  }

  fun decreaseQuantity(listId: String, product: UserListProductEntity) {
    product.quantity = product.quantity?.minus(1.0)
    productUseCase.updateProduct(listId, product).onEach {

    }.launchIn(viewModelScope)
  }

  fun removeProduct(product: UserListProductEntity) {
    productUseCase.removeProduct(product)
      .onEach { result ->
        result.onSuccess {

        }.onError {

        }

      }.launchIn(viewModelScope)
  }
}

sealed class ListDetailViewState {
  object Initial : ListDetailViewState()
  object Loading : ListDetailViewState()
  data class AddProduct(
    val userListProductEntity: UserListProductEntity
  ) : ListDetailViewState()

  data class UserListContent(
    val userListWithProducts: UserListWithProducts,
    val list: List<ProductEntitySummary>,
    val query: String
  ) : ListDetailViewState()

  data class ShowContent(val categoryList: List<CategoryWithProducts>) : ListDetailViewState()
  data class Error(var errorMessage: String) : ListDetailViewState()
}
