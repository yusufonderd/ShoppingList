package com.yonder.addtolist.scenes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.scenes.detail.domain.category.CategoryListUseCase
import com.yonder.addtolist.scenes.detail.domain.product.AddProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
  private val userListUseCase: CategoryListUseCase,
  private val productUseCase: AddProductUseCase


) : ViewModel() {

  private val _state: MutableStateFlow<ListDetailViewState> =
    MutableStateFlow(ListDetailViewState.Initial)
  val state: StateFlow<ListDetailViewState> get() = _state

  init {
    fetchCategories()
  }

  private fun fetchCategories() {
    userListUseCase.getCategories()
      .onEach { result ->
        result.onSuccess {
          _state.value = ListDetailViewState.ShowContent(it).also {
            getPopularProducts()
          }
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

  private fun getProductsByQuery(query: String) {
    userListUseCase.fetchProductByQuery(query)
      .onEach { result ->
        result.onSuccess {
          _state.value = ListDetailViewState.QueryResult(it)
        }
      }.launchIn(viewModelScope)
  }

  private fun getPopularProducts() {
    userListUseCase.fetchPopularProducts()
      .onEach { result ->
        result.onSuccess {
          _state.value = ListDetailViewState.PopularProducts(it)
        }
      }.launchIn(viewModelScope)
  }

  fun addProduct(userListUUID: String, product: ProductEntitySummary) {
    productUseCase.addProduct(userListUUID, product)
      .onEach { result ->
        result.onSuccess {
          Timber.d("addProduct onSuccess => $it")
        }.onError {
          Timber.d("addProduct onError => $it")
        }.onLoading {
          Timber.d("addProduct onLoading")
        }

      }.launchIn(viewModelScope)
  }

}

sealed class ListDetailViewState {
  object Initial : ListDetailViewState()
  object Loading : ListDetailViewState()
  data class PopularProducts(val list: List<ProductEntitySummary>) : ListDetailViewState()
  data class QueryResult(val list: List<ProductEntitySummary>) : ListDetailViewState()
  data class ShowContent(val categoryList: List<CategoryWithProducts>) : ListDetailViewState()
  data class Error(var errorMessage: String) : ListDetailViewState()
}
