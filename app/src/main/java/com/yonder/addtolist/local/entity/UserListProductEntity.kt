package com.yonder.addtolist.local.entity

import android.content.Context
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yonder.addtolist.R
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.scenes.productdetail.model.enums.ProductUnitType
import kotlinx.parcelize.Parcelize
import java.lang.Exception
import java.text.DecimalFormat

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */

const val CATEGORY_OTHER_NAME = "Other"
const val CATEGORY_OTHER_IMAGE = "U+1F3F7"

@Parcelize
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
) : Parcelable {

  fun wrappedCategoryImage(): String {
    val image = (categoryImage ?: CATEGORY_OTHER_IMAGE)
    if (image.isNotEmpty()) {
      return try {
        val codepoint: Int = image.substring(2).toInt(16)
        val chars = Character.toChars(codepoint)
        String(chars)
      } catch (e: Exception) {
        EMPTY_STRING
      }
    }
    return image
  }

  fun getTotalPrice() = price.orZero() * quantity.orZero()

  fun wrappedQuantityWith(context: Context): String {
    val decimalFormat = DecimalFormat("#.#")
    val formatted = decimalFormat.format(quantity)
    val formattedUnit: String
    when (unit) {
      ProductUnitType.Piece.value -> {
        formattedUnit = context.getString(R.string.unit_piece)
      }
      ProductUnitType.Package.value -> {
        formattedUnit = context.getString(R.string.unit_package)
      }
      ProductUnitType.Kg.value -> {
        formattedUnit = context.getString(R.string.unit_kg)
      }
      ProductUnitType.Lt.value -> {
        formattedUnit = context.getString(R.string.unit_lt)
      }
      else -> {
        formattedUnit = EMPTY_STRING
      }
    }
    return "x$formatted $formattedUnit"
  }

  fun wrappedFavorite(): Boolean {
    return favorite == 1
  }

  fun wrappedDone(): Boolean {
    return done == 1
  }
}