package com.yonder.addtolist.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Exception

/**
 * Yusuf Onder on 12,May,2021
 */


@Entity(tableName = "category")
class CategoryEntity(
  @field:ColumnInfo(name = "categoryId") @PrimaryKey val categoryId: String,
  @field:ColumnInfo(name = "name") val name: String,
  @field:ColumnInfo(name = "image") val image: String,
  @field:ColumnInfo(name = "language_id") val languageId: Int
){
  fun wrappedCategoryImage(): String {
    val image = image
    if (image.isNotEmpty()) {
      return try {
        val codepoint: Int = image.substring(2).toInt(16)
        val chars = Character.toChars(codepoint)
        String(chars)
      } catch (e: Exception) {
        ""
      }
    }
    return image
  }

  fun wrappedFormattedName(): String{
    return "${wrappedCategoryImage()} $name"
  }
}
