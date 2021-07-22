package com.yonder.addtolist.scenes.detail.domain.product

import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.core.network.exceptions.RoomResultException
import com.yonder.addtolist.core.network.exceptions.ServerResultException
import com.yonder.addtolist.core.network.request.CreateUserListProductRequest
import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.detail.domain.mapper.UserListProductMapper
import com.yonder.addtolist.scenes.list.data.local.datasource.CategoryDataSource
import com.yonder.addtolist.scenes.list.data.remote.ShoppingListApiService
import com.yonder.addtolist.scenes.list.data.remote.response.UserListProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
class ProductRepositoryImpl @Inject constructor(
  private val api: ShoppingListApiService,
  private val localDataSource: CategoryDataSource
) : ProductRepository {
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