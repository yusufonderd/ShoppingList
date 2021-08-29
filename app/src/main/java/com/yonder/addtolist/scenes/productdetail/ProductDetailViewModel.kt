package com.yonder.addtolist.scenes.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.data.SingleLiveEvent
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.productdetail.domain.DeleteProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetCategoriesUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.UpdateProductUseCase
import com.yonder.addtolist.scenes.productdetail.model.enums.DoneType
import com.yonder.addtolist.scenes.productdetail.model.enums.FavoriteType
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

  private val _state: SingleLiveEvent<ProductDetailViewState> =
    SingleLiveEvent()
  val state: SingleLiveEvent<ProductDetailViewState> get() = _state

  fun fetchProductId(selectedProductId: Int?) {
    if (selectedProductId != null) {
      val flow1 = getCategoriesUseCase.invoke()
      val flow2 = getProductUseCase.invoke(selectedProductId)
      flow1.combine(flow2) { categories, productEntity ->
        val categoryOfProduct = CategoryFinder(categories).find(productEntity.categoryImage)
        _state.value = ProductDetailViewState.Load(
          categories = categories,
          product = productEntity,
          categoryOfProduct = categoryOfProduct
        )
      }.launchIn(viewModelScope)
    }
  }

  fun toggleFavorite(item: UserListProductEntity) {
    if (item.wrappedFavorite()) {
      item.favorite = FavoriteType.UnFavorite.value
    } else {
      item.favorite = FavoriteType.Favorite.value
    }
    update(item)
  }


  private fun update(product: UserListProductEntity) {
    viewModelScope.launch {
      updateProductUseCase(product)
    }
  }

  fun done(product: UserListProductEntity) {
    if (product.wrappedDone()) {
      product.done = DoneType.UnDone.value
    } else {
      product.done = DoneType.Done.value
    }
    update(product)
  }

  fun delete(product: UserListProductEntity) {
    viewModelScope.launch {
      deleteProductUseCase(product)
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

  fun updateCategory(product: UserListProductEntity, categoryPosition: Int) {
    val viewState = state.value
    if (viewState is ProductDetailViewState.Load) {
      viewState.categories.getOrNull(categoryPosition)?.let { selectedCategory ->
        if (selectedCategory.image != product.categoryImage) {
          product.categoryName = selectedCategory.name
          product.categoryImage = selectedCategory.image
          update(product)
        }
      }
    }
  }

}

