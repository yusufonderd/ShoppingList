package com.yonder.addtolist.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
data class CategoryWithProducts(
  @Embedded val category: CategoryEntity,
  @Relation(
    parentColumn = "categoryId",
    entityColumn = "parentCategoryId"
  )
  val products: List<ProductEntity>
)