package com.yonder.addtolist.scenes.detail.domain

import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.core.mapper.Mapper
import com.yonder.addtolist.core.network.responses.Result
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.scenes.list.data.local.datasource.CategoryDataSource
import com.yonder.addtolist.scenes.list.data.remote.ShoppingListApiService
import com.yonder.addtolist.scenes.list.domain.mapper.CategoryProductsMapper
import com.yonder.addtolist.scenes.list.domain.model.ProductUiModel
import com.yonder.addtolist.scenes.list.domain.model.TranslationUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
class CategoryListRepositoryImpl @Inject constructor(
  private val apiService: ShoppingListApiService,
  private val categoryDataSource: CategoryDataSource,
  private val mapper: CategoryProductsMapper
) : CategoryListRepository {

  override fun getCategories(): Flow<Result<List<CategoryEntity>>> = flow {
    emit(Result.Loading)
    val result = apiService.getCategories(null)
    val entities = mapper.map(result)
    entities.list.forEach { category ->
      val categories = ListMapperImpl(
        CategoryEntityMapper(
          categoryId = "${category.id}",
          categoryImage = category.image
        )
      ).map(category.translationResponses)
      categoryDataSource.insertAll(categories)
      val products = ListMapperImpl(
        ProductEntityMapper(
          categoryImage = category.image,
          categoryId = "${category.id}",
          categoryName = category.name
        )
      ).map(category.products)
      categoryDataSource.insertAllProducts(products)
    }

  }
}

class CategoryEntityMapper(
  private val categoryId: String,
  private val categoryImage: String
) : Mapper<TranslationUiModel, CategoryEntity> {
  override fun map(input: TranslationUiModel): CategoryEntity {
    return CategoryEntity(categoryId, input.name, categoryImage, input.languageId)
  }
}


class ProductEntityMapper(
  private val categoryId: String,
  private val categoryName: String,
  private val categoryImage: String
) : Mapper<ProductUiModel, ProductEntity> {
  override fun map(input: ProductUiModel): ProductEntity {
    return ProductEntity(
      input.id,
      categoryId,
      categoryName,
      input.isPopular,
      input.languageId,
      categoryImage
    )
  }
}