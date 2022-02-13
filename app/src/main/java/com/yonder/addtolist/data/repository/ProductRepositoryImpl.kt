package com.yonder.addtolist.data.repository

import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.core.network.exceptions.ServerResultException
import com.yonder.addtolist.core.network.request.CreateUserListProductRequest
import com.yonder.core.network.RestResult
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.domain.uimodel.UserListProductMapper
import com.yonder.addtolist.data.datasource.CategoryDataSource
import com.yonder.addtolist.data.datasource.UserListDataSource
import com.yonder.addtolist.network.ApiService
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
class ProductRepositoryImpl @Inject constructor(
  private val api: ApiService,
  private val userPreferenceDataStore: UserPreferenceDataStore,
  private val categoryDataSource: CategoryDataSource,
  private val userListDataSource: UserListDataSource
) : ProductRepository {

  override fun getProductEntityByName(productName: String): Flow<ProductEntity?> = flow {
    val popularProducts =
      categoryDataSource.getProductByEntity(productName, userPreferenceDataStore.getAppLanguageId())
    emit(popularProducts)
  }

  override fun updateProduct(
    listId: String,
    product: UserListProductEntity
  ): Flow<RestResult<UserListProductEntity>> = flow {
    emit(RestResult.Loading)
    val request = UserListProductMapper.mapEntityToResponse(listId, product)
    val response = api.updateProduct(product.id, request)
    if (response.success == true) {
      categoryDataSource.update(product)
    }
    emit(RestResult.Success(product))
  }.catch { e ->
    e.printStackTrace()
    emit(RestResult.Error(e))
  }

  override suspend fun delete(product: UserListProductUiModel) {
    val productEntity = userListDataSource.findProduct(
      listUUID = product.listUUID,
      productName = product.name
    )
    api.removeProduct(product.id)
    categoryDataSource.delete(productEntity)
  }

  override fun addProduct(
    listUUID: String,
    product: CreateUserListProductRequest
  ): Flow<RestResult<UserListProductEntity>> = flow {
    emit(RestResult.Loading)

    val entity = UserListProductMapper.mapRequestToEntity(listUUID, product)
    val response = api.createProduct(product)

    if (response.success == true && response.data != null) {
      entity.id = response.data.id.orZero()
    } else {
      emit(RestResult.Error<UserListProductEntity>(ServerResultException()))
    }
    categoryDataSource.insert(entity)
    emit(RestResult.Success<UserListProductEntity>(entity))
  }.catch { e ->
    e.printStackTrace()
    categoryDataSource.insert(
      UserListProductMapper.mapRequestToEntity(listUUID, product)
    )
    emit(RestResult.Error(e))
  }
}
