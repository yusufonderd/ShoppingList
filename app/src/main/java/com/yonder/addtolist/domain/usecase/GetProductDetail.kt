package com.yonder.addtolist.domain.usecase

import com.yonder.addtolist.core.base.UseCase
import com.yonder.addtolist.domain.uimodel.CategoryUiModel
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.domain.decider.CategoryFinder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */
class GetProductDetail @Inject constructor(
    private val getProductUseCase: GetUserListProduct,
    private val getCategoriesUseCase: GetCategories
) : UseCase<Int, GetProductDetail.Param?> {

    override suspend fun invoke(input: Int): Flow<Param?> = flow {
        val flow1 = getCategoriesUseCase.invoke()
        val flow2 = getProductUseCase.invoke(input)
        combine(flow1, flow2) { categories, product ->
            if (product != null) {
                val categoryOfProduct = CategoryFinder(categories).find(product.categoryImage)
                emit(
                    Param(
                        categories = categories,
                        product = product,
                        categoryOfProduct = categoryOfProduct
                    )
                )
            }
        }.collect()
    }

    data class Param(
        var categories: List<CategoryUiModel>,
        var product: UserListProductUiModel,
        var categoryOfProduct: CategoryUiModel?
    )
}