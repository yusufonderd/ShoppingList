package com.yonder.addtolist.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Exception

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */

 const val CATEGORY_OTHER_NAME = "Other"
 const val CATEGORY_OTHER_IMAGE = "U+1F3F7"

@Entity(tableName = "user_list_products")
class UserListProductEntity(
  @field:ColumnInfo(name = "id") @PrimaryKey var id: Int? = null,
  @field:ColumnInfo(name = "userListUUID") val listUUID: String,
  @field:ColumnInfo(name = "name") var name: String?,
  @field:ColumnInfo(name = "category_image") var categoryImage: String? = CATEGORY_OTHER_IMAGE,
  @field:ColumnInfo(name = "category_name") var categoryName: String? = CATEGORY_OTHER_NAME,
  @field:ColumnInfo(name = "note") var note: String? = "",
  @field:ColumnInfo(name = "unit") var unit: String? = null,
  @field:ColumnInfo(name = "done") var done: Int? = 0,
  @field:ColumnInfo(name = "favorite") var favorite: Int? = 0,
  @field:ColumnInfo(name = "quantity") var quantity: Double? = 1.0,
  @field:ColumnInfo(name = "price") var price: Double? = 0.0,
  @field:ColumnInfo(name = "sync") var sync: Boolean? = false
){

  fun wrappedCategoryImage(): String {
    val image = (categoryImage ?: CATEGORY_OTHER_IMAGE)
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
}