package com.yonder.addtolist.scenes.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.data.SingleLiveEvent
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.productdetail.domain.DeleteProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetCategoriesUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.UpdateProductUseCase
import com.yonder.addtolist.scenes.productdetail.model.enums.ProductUnitType
import com.yonder.addtolist.scenes.productdetail.utils.CategoryFinder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
@Suppress("TooManyFunctions")
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
  private val getProductUseCase: GetProductUseCase,
  private val deleteProductUseCase: DeleteProductUseCase,
  private val updateProductUseCase: UpdateProductUseCase,
  private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

  private val _state: SingleLiveEvent<ProductDetailViewState> =
    SingleLiveEvent()
  val state: SingleLiveEvent<ProductDetailViewState> get() = _state

  fun fetchProductId(selectedProductId: Int?) {
    if (selectedProductId != null) {
      val flow1 = getCategoriesUseCase.invoke()
      val flow2 = getProductUseCase.invoke(selectedProductId)
      flow1.combine(flow2) { categories, product ->
        val categoryOfProduct = CategoryFinder(categories).find(product.categoryImage)
        _state.value = ProductDetailViewState.Load(
          categories = categories,
          product = product,
          categoryOfProduct = categoryOfProduct
        )
      }.launchIn(viewModelScope)
    }
  }


  fun toggleFavorite(product: UserListProductUiModel) {
    product.isFavorite = !product.isFavorite
    viewModelScope.launch {
      updateProductUseCase(product)
    }
  }

  fun toggleDone(product: UserListProductUiModel) {
    product.isDone = !product.isDone
    viewModelScope.launch {
      updateProductUseCase(product)
    }
  }

  fun delete(product: UserListProductUiModel) {
    viewModelScope.launch {
      deleteProductUseCase.delete(product).collect()
    }
  }


  fun increaseQuantity(product: UserListProductUiModel) {
    viewModelScope.launch {
      product.quantityValue = product.quantityValue.plus(1.0)
      updateProductUseCase(product)
    }
  }


  fun decreaseQuantity(product: UserListProductUiModel) {
    product.quantityValue = product.quantityValue.minus(1.0)
    viewModelScope.launch {
      updateProductUseCase(product)
    }
  }

  fun updateUnit(product: UserListProductUiModel, unit: ProductUnitType) {
    if (product.unit != unit.value) {
      product.unit = unit.value
      viewModelScope.launch {
        updateProductUseCase(product)
      }
    }
  }

  fun updateProductName(product: UserListProductUiModel, name: String) {
    if (product.name != name) {
      product.name = name
      viewModelScope.launch {
        updateProductUseCase(product)
      }
    }
  }

  fun updateProductPrice(product: UserListProductUiModel, price: Double?) {
    Timber.d("updateProductPrice => $price")
    if (product.priceValue != price) {
      product.priceValue = price.orZero()
      viewModelScope.launch {
        updateProductUseCase(product)
      }
    }
  }

  fun updateProductNote(product: UserListProductUiModel, note: String?) {
    if (product.note != note) {
      product.note = note.orEmpty()
      viewModelScope.launch {
        updateProductUseCase(product)
      }
    }
  }

  fun updateCategory(product: UserListProductUiModel,categoryPosition: Int) {
    Timber.d("updateCategory => $product $categoryPosition")
    val viewState = state.value
    if (viewState is ProductDetailViewState.Load){
      viewState.categories.getOrNull(categoryPosition)?.let { selectedCategoryUIModel ->
        if (selectedCategoryUIModel.image != product.categoryImage) {
          product.categoryImage = selectedCategoryUIModel.image
          product.name = selectedCategoryUIModel.name
          Timber.e("updateCategory2 => $product $categoryPosition")
           viewModelScope.launch {
             updateProductUseCase(product)
           }
        }
      }
    }
  }

}

