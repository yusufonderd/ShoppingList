package com.yonder.addtolist.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@Entity(tableName = "category_products")
class ProductEntity(
  @field:ColumnInfo(name = "id") @PrimaryKey val id: Int?,
  @field:ColumnInfo(name = "parentCategoryId") val parentCategoryId: String?,
  @field:ColumnInfo(name = "name") val name: String,
  @field:ColumnInfo(name = "popular") val popular: Boolean,
  @field:ColumnInfo(name = "languageId") val languageId: Int,
  @field:ColumnInfo(name = "categoryImage") val categoryImage: String?)

data class ProductEntitySummary(val name:  String,var categoryImage: String?)
