package com.yonder.addtolist.scenes.detail.domain.product

import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.core.network.exceptions.ServerResultException
import com.yonder.addtolist.core.network.request.CreateUserListProductRequest
import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.detail.domain.mapper.UserListProductMapper
import com.yonder.addtolist.scenes.home.data.local.datasource.CategoryDataSource
import com.yonder.addtolist.scenes.home.data.remote.ApiService
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
  private val localDataSource: CategoryDataSource
) : ProductRepository {

  override fun getProductEntityByName(productName: String): Flow<ProductEntity?> = flow {
    val popularProducts =
      localDataSource.getProductByEntity(productName, userPreferenceDataStore.getAppLanguageId())
    emit(popularProducts)
  }

  override fun updateProduct(
    listId: String,
    product: UserListProductEntity
  ): Flow<Result<UserListProductEntity>> = flow {
    emit(Result.Loading)
    val request = UserListProductMapper.mapEntityToResponse(listId, product)
    val response = api.updateProduct(product.id, request)
    if (response.success == true) {
      localDataSource.update(product)
    }
    emit(Result.Success<UserListProductEntity>(product))
  }.catch { e ->
    e.printStackTrace()
    emit(Result.Error(e))
  }

  override fun removeProduct(
    product: UserListProductEntity
  ): Flow<Result<UserListProductEntity>> = flow {
    emit(Result.Loading)
    val response = api.removeProduct(product.id)
    if (response.success == true) {
      localDataSource.delete(product)
    }
    emit(Result.Success<UserListProductEntity>(product))
  }.catch { e ->
    e.printStackTrace()
    emit(Result.Error(e))
  }

  override fun addProduct(
    listUUID: String,
    product: CreateUserListProductRequest
  ): Flow<Result<UserListProductEntity>> = flow {
    emit(Result.Loading)

    val entity = UserListProductMapper.mapRequestToEntity(listUUID, product)
    val response = api.createProduct(product)

    if (response.success == true && response.data != null) {
      entity.id = response.data.id.orZero()
    } else {
      emit(Result.Error<UserListProductEntity>(ServerResultException()))
    }
    localDataSource.insert(entity)
    emit(Result.Success<UserListProductEntity>(entity))
  }.catch { e ->
    e.printStackTrace()
    localDataSource.insert(
      UserListProductMapper.mapRequestToEntity(listUUID, product)
    )
    emit(Result.Error(e))
  }
}
