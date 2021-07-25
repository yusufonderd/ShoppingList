package com.yonder.addtolist.scenes.productdetail.domain.usecase

import androidx.lifecycle.LiveData
import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */

interface LocalProductUseCase {
  suspend fun delete(product: UserListProductEntity)
  suspend fun update(product: UserListProductEntity)
  fun getProductById(id: Int?): Flow<UserListProductEntity?>
  fun getCategories() : Flow<List<CategoryEntity>>
}

class LocalProductUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase,
  private val userPreferenceDataStore: UserPreferenceDataStore,
  private val dispatcher: CoroutineThread
) : LocalProductUseCase {

  override suspend fun update(product: UserListProductEntity) {
    return appDatabase.userListProductDao().update(product)
  }

  override suspend fun delete(product: UserListProductEntity) {
    return appDatabase.userListProductDao().delete(product)
  }
  override fun getProductById(id: Int?):  Flow<UserListProductEntity?> {
    return appDatabase.userListProductDao().findById(id).flowOn(dispatcher.io)
  }

  override fun getCategories(): Flow<List<CategoryEntity>> {
    return appDatabase.categoryDao().findByLanguageId(userPreferenceDataStore.getAppLanguageId()).flowOn(dispatcher.io)
  }
}