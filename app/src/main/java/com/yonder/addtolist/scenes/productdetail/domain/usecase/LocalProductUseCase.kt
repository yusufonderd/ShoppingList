package com.yonder.addtolist.scenes.productdetail.domain.usecase

import androidx.lifecycle.LiveData
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 25.07.2021
 */

interface LocalProductUseCase {
  fun update(product: UserListProductEntity)
  fun getProductById(id: Int?): LiveData<UserListProductEntity>
  fun getCategories(languageId: Int) : LiveData<List<CategoryEntity>>
}

class LocalProductUseCaseImpl @Inject constructor(
  private val appDatabase: AppDatabase
) : LocalProductUseCase {
  override fun update(product: UserListProductEntity) {
    return appDatabase.userListProductDao().update(product)
  }

  override fun getProductById(id: Int?):  LiveData<UserListProductEntity> {
    return appDatabase.userListProductDao().findById(id)
  }

  override fun getCategories(languageId: Int): LiveData<List<CategoryEntity>> {
    return appDatabase.categoryDao().findByLanguageId(languageId)
  }
}