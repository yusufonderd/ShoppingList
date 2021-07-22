package com.yonder.addtolist.scenes.detail.domain.category

import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.core.network.exceptions.RoomResultException
import com.yonder.addtolist.core.network.exceptions.ServerResultException
import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.local.entity.CategoryWithProducts
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.scenes.detail.domain.mapper.CategoryEntityMapper
import com.yonder.addtolist.scenes.detail.domain.mapper.ProductEntityMapper
import com.yonder.addtolist.scenes.list.data.local.datasource.CategoryDataSource
import com.yonder.addtolist.scenes.list.data.remote.ApiService
import com.yonder.addtolist.scenes.list.domain.mapper.CategoryProductsMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
class CategoryListRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  private val categoryDataSource: CategoryDataSource,
  private val userPreferenceDataStore: UserPreferenceDataStore,
  private val mapper: CategoryProductsMapper
) : CategoryListRepository {

  override fun fetchWord(query: String): Flow<Result<List<ProductEntitySummary>>> = flow {
    emit(Result.Loading)
    emit(Result.Success(categoryDataSource.getProductsByQuery(query)))
  }.catch { e ->
    e.printStackTrace()
    emit(Result.Error(RoomResultException()))
  }

  override fun fetchPopularProducts(): Flow<List<ProductEntitySummary>> = flow {
    val popularProducts = categoryDataSource.getPopularProducts()
    emit((popularProducts))
  }

  override fun fetchCategories(): Flow<Result<List<CategoryWithProducts>>> = flow {
    if (!userPreferenceDataStore.isFetchedCategories()) {
      emit(Result.Loading)
      val result = apiService.getCategories(null)
      val entities = mapper.map(result)
      entities.list.forEach { category ->
        val categories = ListMapperImpl(
          CategoryEntityMapper(categoryImage = category.image)
        ).map(category.translationResponses)
        val products = ListMapperImpl(
          ProductEntityMapper(
            categoryImage = category.image,
            categoryId = "${category.id}"
          )
        ).map(category.products)
        categoryDataSource.insertAll(categories)
        categoryDataSource.insertAllProducts(products)
        userPreferenceDataStore.setFetchedCategories()
      }
    }
    emit(Result.Success(categoryDataSource.getCategories()))
  }.catch { e ->
    e.printStackTrace()
    emit(Result.Error(ServerResultException()))
  }
}




