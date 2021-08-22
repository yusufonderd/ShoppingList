package com.yonder.addtolist.scenes.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.data.SingleLiveEvent
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.productdetail.domain.LocalProductUseCase
import com.yonder.addtolist.scenes.productdetail.model.ProductUnitType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FAVORITE_VALUE = 1
private const val NO_FAVORITE_VALUE = 0

private const val DONE_VALUE = 1
private const val NO_DONE_VALUE = 0


/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
  private val productUseCase: LocalProductUseCase
) : ViewModel() {

  private val _state: SingleLiveEvent<ProductDetailViewState> =
    SingleLiveEvent()
  val state: SingleLiveEvent<ProductDetailViewState> get() = _state

  fun fetchProductId(selectedProductId: Int?) {
    if (selectedProductId != null) {
      val flow1 = productUseCase.getCategories()
      val flow2 = productUseCase.getProductById(selectedProductId)
      flow1.combine(flow2) { categories, productEntity ->
        _state.value = ProductDetailViewState.Load(categories, productEntity)
      }.launchIn(viewModelScope)
    }
  }

  fun toggleFavorite(item: UserListProductEntity) {
    if (item.wrappedFavorite()) {
      item.favorite = NO_FAVORITE_VALUE
    } else {
      item.favorite = FAVORITE_VALUE
    }
    update(item)
  }


  private fun update(product: UserListProductEntity) {
    viewModelScope.launch {
      productUseCase.update(product)
    }
  }

  fun done(product: UserListProductEntity) {
    if (product.wrappedDone()) {
      product.done = NO_DONE_VALUE
    } else {
      product.done = DONE_VALUE
    }
    update(product)
  }

  fun delete(product: UserListProductEntity) {
    viewModelScope.launch {
      productUseCase.delete(product)
    }
  }

  fun increaseQuantity(product: UserListProductEntity) {
    product.quantity = product.quantity.orZero().plus(1)
    update(product)
  }

  fun decreaseQuantity(product: UserListProductEntity) {
    product.quantity = product.quantity.orZero().minus(1)
    update(product)
  }

  fun updateUnit(product: UserListProductEntity, unit: ProductUnitType) {
    if (product.unit != unit.value) {
      product.unit = unit.value
      update(product)
    }
  }

  fun updateProductName(product: UserListProductEntity, name: String?) {
    if (product.name != name && name.isNullOrEmpty().not()) {
      product.name = name
      update(product)
    }
  }

  fun updateProductPrice(product: UserListProductEntity, price: Double?) {
    if (product.price != price && price != null) {
      product.price = price
      update(product)
    }
  }

  fun updateProductNote(product: UserListProductEntity, note: String?) {
    if (product.note != note) {
      product.note = note
      update(product)
    }
  }

  fun updateCategory(product: UserListProductEntity, category: CategoryEntity) {
    if (category.image != product.categoryImage) {
      product.categoryName = category.name
      product.categoryImage = category.image
      update(product)
    }
  }

}

