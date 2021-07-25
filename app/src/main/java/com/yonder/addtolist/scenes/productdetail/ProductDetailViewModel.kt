package com.yonder.addtolist.scenes.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonder.addtolist.common.utils.device.DeviceUtils
import com.yonder.addtolist.core.data.CombinedLiveData
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.productdetail.domain.usecase.LocalProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FAVORITE_VALUE = 1
private const val NO_FAVORITE_VALUE = 0

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
  private val productUseCase: LocalProductUseCase
) : ViewModel() {

  private var selectedProductId: MutableLiveData<Int> = MutableLiveData()

  var categories: LiveData<List<CategoryEntity>> =
    productUseCase.getCategories(1)

  private val userListProduct: LiveData<UserListProductEntity> =
    Transformations.switchMap(selectedProductId) {
      productUseCase.getProductById(it)
    }

  val combinedLiveData: CombinedLiveData<ProductDetailViewState> =
    CombinedLiveData(categories, userListProduct) { datas ->
      val categories: List<CategoryEntity>? = (datas[0] as? List<CategoryEntity>)
      val userListProduct: UserListProductEntity? = (datas[1] as? UserListProductEntity)
      return@CombinedLiveData ProductDetailViewState.Load(categories.orEmpty(), userListProduct)
    }

  fun toggleFavorite(item: UserListProductEntity) {
    if (item.wrappedFavorite()) {
      item.favorite = NO_FAVORITE_VALUE
    } else {
      item.favorite = FAVORITE_VALUE
    }
    update(item)
  }

  fun getProductById(productId: Int?) {
    selectedProductId.postValue(productId)
  }

  private fun update(product: UserListProductEntity) {
    viewModelScope.launch {
      productUseCase.update(product)
    }
  }


  fun updateCategory(newCategory: CategoryEntity, product: UserListProductEntity) {
    if (newCategory.image != product.categoryImage) {
      product.categoryName = newCategory.name
      product.categoryImage = newCategory.image
      update(product)
    }
  }


}