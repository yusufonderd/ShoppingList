package com.yonder.addtolist.scenes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

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

  init {
    fetchCategories()
  }

  fun fetchProductsByUUID(listUUID: String) {
    localUserListUseCase.getUserListByUUID(listUUID).onEach {
      _state.value = ListDetailViewState.UserListContent(it)
    }.launchIn(viewModelScope)
  }

  private fun fetchCategories() {
    categoryListUseCase.getCategories()
      .onEach { result ->
        result.onSuccess {
          _state.value = ListDetailViewState.ShowContent(it)
          getPopularProducts()
        }.onLoading {
          _state.value = ListDetailViewState.Loading
        }.onError { error ->
          _state.value = ListDetailViewState.Error(error.toReadableMessage())
        }
      }.launchIn(viewModelScope)
  }

  fun searchBy(query: String) {
    if (query.trim().isEmpty()) {
      getPopularProducts()
    } else {
      getProductsByQuery(query)
    }
  }


  private fun getPopularProducts() {
    categoryListUseCase.fetchPopularProducts()
      .onEach {
        _state.value = ListDetailViewState.PopularProducts(it)
      }.launchIn(viewModelScope)
  }

  private fun getProductsByQuery(query: String) {
    categoryListUseCase.fetchProductByQuery(query)
      .onEach { result ->
        result.onSuccess {
          _state.value = ListDetailViewState.QueryResult(it)
        }
      }.launchIn(viewModelScope)
  }


  fun addProduct(listId: String, userListUUID: String, product: ProductEntitySummary) {
    productUseCase.addProduct(
      listId = listId,
      listUUID = userListUUID,
      product = product
    ).onEach {

      }.launchIn(viewModelScope)
  }

  fun increaseQuantity(listId: String,product: UserListProductEntity) {
    product.quantity = product.quantity?.plus(1.0)
    productUseCase.updateProduct(listId,product).onEach {
      Timber.d("updateProduct => $it")
    }.launchIn(viewModelScope)
  }

  fun decreaseQuantity(listId: String,product: UserListProductEntity) {
    product.quantity = product.quantity?.minus(1.0)
    productUseCase.updateProduct(listId,product).onEach {

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
    val userListWithProducts: UserListWithProducts
  ) : ListDetailViewState()


  data class PopularProducts(
    val list: List<ProductEntitySummary>,
  ) : ListDetailViewState()

  data class QueryResult(val list: List<ProductEntitySummary>) : ListDetailViewState()
  data class ShowContent(val categoryList: List<CategoryWithProducts>) : ListDetailViewState()
  data class Error(var errorMessage: String) : ListDetailViewState()
}
