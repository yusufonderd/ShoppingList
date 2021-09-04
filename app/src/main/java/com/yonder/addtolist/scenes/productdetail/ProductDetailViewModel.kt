package com.yonder.addtolist.scenes.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.data.SingleLiveEvent
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.scenes.home.domain.model.CategoryUiModel
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.productdetail.domain.DeleteProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetCategoriesUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.UpdateProductUseCase
import com.yonder.addtolist.scenes.productdetail.model.enums.ProductUnitType
import com.yonder.addtolist.scenes.productdetail.utils.CategoryFinder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
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

  private val _event: SingleLiveEvent<ProductDetailViewEvent> =
    SingleLiveEvent()
  val event: SingleLiveEvent<ProductDetailViewEvent> get() = _event

  fun fetchProductId(selectedProductId: Int?) {
    if (selectedProductId != null) {
      val flow1 = getCategoriesUseCase.invoke()
      val flow2 = getProductUseCase.invoke(selectedProductId)
      flow1.combine(flow2) { categories, product ->
        if (product != null) {
          val categoryOfProduct = CategoryFinder(categories).find(product.categoryImage)
          _event.value = ProductDetailViewEvent.Load(
            categories = categories,
            product = product,
            categoryOfProduct = categoryOfProduct
          )
        } else {
          _event.value = ProductDetailViewEvent.ProductNotFound
        }
      }.launchIn(viewModelScope)
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
      deleteProductUseCase(product)
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
      updateProductUseCase(
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

