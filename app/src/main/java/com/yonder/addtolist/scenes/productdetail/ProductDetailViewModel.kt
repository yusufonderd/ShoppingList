package com.yonder.addtolist.scenes.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.core.data.SingleLiveEvent
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.productdetail.domain.DeleteProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetCategoriesUseCase
import com.yonder.addtolist.scenes.productdetail.domain.GetProductUseCase
import com.yonder.addtolist.scenes.productdetail.domain.UpdateProductUseCase
import com.yonder.addtolist.scenes.productdetail.model.enums.DoneType
import com.yonder.addtolist.scenes.productdetail.model.enums.FavoriteType
import com.yonder.addtolist.scenes.productdetail.model.enums.ProductUnitType
import com.yonder.addtolist.scenes.productdetail.utils.CategoryFinder
import com.yonder.uicomponent.base.model.UserListProductUiModel
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


  private fun update(product: UserListProductEntity) {
    viewModelScope.launch {
      updateProductUseCase(product)
    }
  }


  fun toggleFavorite() {
    getProductEntity { product ->
      if (product.wrappedFavorite()) {
        product.favorite = FavoriteType.UnFavorite.value
      } else {
        product.favorite = FavoriteType.Favorite.value
      }
      update(product)
    }
  }

  fun done() {
    getProductEntity { product ->
      if (product.wrappedDone()) {
        product.done = DoneType.UnDone.value
      } else {
        product.done = DoneType.Done.value
      }
      update(product)
    }

  }

  fun delete() {
    viewModelScope.launch {
      getProductEntity { product ->
        deleteProductUseCase(product)
      }
    }
  }

  fun increaseQuantity() {
    getProductEntity { product ->
      product.quantity = product.quantity.orZero().plus(1)
      update(product)
    }
  }

  fun decreaseQuantity() {
    getProductEntity { product ->
      product.quantity = product.quantity.orZero().minus(1)
      update(product)
    }
  }

  fun updateUnit(unit: ProductUnitType) {
    getProductEntity { product ->
      if (product.unit != unit.value) {
        product.unit = unit.value
        update(product)
      }
    }
  }

  fun updateProductName(name: String?) {
    getProductEntity { product ->
      if (product.name != name && name.isNullOrEmpty().not()) {
        product.name = name
        update(product)
      }
    }

  }

  fun updateProductPrice(price: Double?) {
    getProductEntity { product ->
      if (product.price != price && price != null) {
        product.price = price
        update(product)
      }
    }
  }

  fun updateProductNote(note: String?) {
    getProductEntity { product ->
      if (product.note != note) {
        product.note = note
        update(product)
      }
    }
  }

  fun updateCategory(product: UserListProductUiModel, categoryPosition: Int) {
    getCategoryEntity(categoryPosition) { categoryEntity: CategoryEntity ->
      getProductEntity { userListProductEntity: UserListProductEntity ->
        if (categoryEntity.image != product.categoryImage) {
          userListProductEntity.categoryName = categoryEntity.name
          userListProductEntity.categoryImage = categoryEntity.image
          update(userListProductEntity)
        }
      }
    }
  }

  private inline fun getProductEntity(
    productInvoker: (UserListProductEntity) -> Unit = {},
  ) {
    val viewState = state.value
    if (viewState is ProductDetailViewState.Load) {
      productInvoker.invoke(viewState.product)
    }
  }

  private inline fun getCategoryEntity(
    position: Int,
    productInvoker: (CategoryEntity) -> Unit = {},
  ) {
    val viewState = state.value
    if (viewState is ProductDetailViewState.Load) {
      viewState.categories.getOrNull(position)?.let { selectedCategory ->
        productInvoker.invoke(selectedCategory)
      }
    }
  }

}

