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

interface ProductRepository {
  fun addProduct(
    listUUID: String,
    product: CreateUserListProductRequest
  ): Flow<RestResult<UserListProductEntity>>

  suspend fun delete(product: UserListProductUiModel)

  fun getProductEntityByName(productName: String): Flow<ProductEntity?>
}


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

  override suspend fun delete(product: UserListProductUiModel) {
    val productEntity = userListDataSource.findProduct(
      listUUID = product.listUUID,
      productName = product.name
    )
    categoryDataSource.delete(productEntity)
  }

  override fun addProduct(
    listUUID: String,
    product: CreateUserListProductRequest
  ): Flow<RestResult<UserListProductEntity>> = flow {
    emit(RestResult.Loading)

    val entity = UserListProductMapper.mapRequestToEntity(listUUID, product)
    categoryDataSource.insert(entity)
    emit(RestResult.Success(entity))
  }.catch { e ->
    e.printStackTrace()
    categoryDataSource.insert(
      UserListProductMapper.mapRequestToEntity(listUUID, product)
    )
    emit(RestResult.Error(e))
  }
}
