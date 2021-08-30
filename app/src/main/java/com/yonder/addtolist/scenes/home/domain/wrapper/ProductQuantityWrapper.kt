package com.yonder.addtolist.scenes.home.domain.wrapper

import android.content.Context
import com.yonder.addtolist.R
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.scenes.productdetail.model.enums.ProductUnitType
import java.text.DecimalFormat

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */
object ProductQuantityWrapper {
  private const val PATTERN_DECIMAL_FORMAT = "#.#"

  fun wrap(context: Context, quantity: Double, unit: String): String {
    val decimalFormat = DecimalFormat(PATTERN_DECIMAL_FORMAT)
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
}