package com.yonder.addtolist.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Yusuf Onder on 12,May,2021
 */

@Entity(tableName = "category")
class CategoryEntity(
  @field:ColumnInfo(name = "categoryId") @PrimaryKey val categoryId: String,
  @field:ColumnInfo(name = "name") val name: String,
  @field:ColumnInfo(name = "image") val image: String,
  @field:ColumnInfo(name = "language_id") val languageId: Int
)
