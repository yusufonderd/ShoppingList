package com.yonder.addtolist.domain.uimodel

import android.os.Parcelable
import com.yonder.addtolist.common.enums.ProductUnitType
import kotlinx.parcelize.Parcelize

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

@Parcelize
data class UserListProductUiModel(
  val id: Int?,
  var listUUID: String,
  var name: String,
  var note: String,
  var categoryName: String,
  var categoryImage: String,
  var categoryUnicode: String,
  var quantity: String,
  var quantityValue: Double,
  var price: String,
  var priceValue: Double,
  var unit: String,
  var isFavorite: Boolean,
  var isDone: Boolean,
  var totalPrice : String,
  var shouldShowNoteField: Boolean,
  var shouldShowQuantityField : Boolean,
  var shouldShowPriceField : Boolean,
  var formattedName: String,
  var unitType: ProductUnitType
) : Parcelable
