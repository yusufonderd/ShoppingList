package com.yonder.addtolist.scenes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.detail.domain.category.CategoryListUseCase
import com.yonder.addtolist.scenes.detail.domain.product.AddProductUseCase
import com.yonder.addtolist.scenes.list.domain.usecase.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import timber.log.Timber
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@HiltViewModel
class ListDetailViewModel @Inject constructor(
  private val categoryListUseCase: CategoryListUseCase,
  private val productUseCase: AddProductUseCase,
  private val userListUseCase: UserListUseCase

) : ViewModel() {

  private val _state: MutableStateFlow<ListDetailViewState> =
    MutableStateFlow(ListDetailViewState.Initial)
  val state: StateFlow<ListDetailViewState> get() = _state


  init {
    fetchCategories()
  }

  fun fetchProductsByUUID(listUUID: String) {
    val flow1 = categoryListUseCase.fetchPopularProducts()
    val flow2 = userListUseCase.getUserListByUUID(listUUID)
    flow1.zip(flow2) { result1, result2 ->
      _state.value = ListDetailViewState.PopularProducts(result1, result2)

    }.launchIn(viewModelScope)


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

  fun searchBy(query: String) {
    if (query.trim().isEmpty()) {
      //getPopularProducts()
    } else {
      getProductsByQuery(query)
    }
  }

  private fun getProductsByQuery(query: String) {
    categoryListUseCase.fetchProductByQuery(query)
      .onEach { result ->
        result.onSuccess {
          _state.value = ListDetailViewState.QueryResult(it)
        }
      }.launchIn(viewModelScope)
  }


  fun addProduct(userListUUID: String, product: ProductEntitySummary) {
    productUseCase.addProduct(userListUUID, product)
      .onEach { result ->
        result.onSuccess {
          Timber.e("addProduct onSuccess => $it")
        }.onError {
          Timber.e("addProduct onError => $it")
        }.onLoading {
          Timber.e("addProduct onLoading")
        }

      }.launchIn(viewModelScope)
  }

}

sealed class ListDetailViewState {
  object Initial : ListDetailViewState()
  object Loading : ListDetailViewState()
  data class PopularProducts(
    val list: List<ProductEntitySummary>,
    val userListWithProducts: UserListWithProducts
  ) : ListDetailViewState()

  data class QueryResult(val list: List<ProductEntitySummary>) : ListDetailViewState()
  data class ShowContent(val categoryList: List<CategoryWithProducts>) : ListDetailViewState()
  data class Error(var errorMessage: String) : ListDetailViewState()
}
