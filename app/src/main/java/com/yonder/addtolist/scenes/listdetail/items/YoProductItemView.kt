package com.yonder.addtolist.scenes.listdetail.items

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import com.yonder.addtolist.core.extensions.INDEX_NOT_FOUND
import com.yonder.addtolist.databinding.LayoutYoProductViewBinding
import com.yonder.addtolist.scenes.listdetail.items.model.ItemUiModel

private const val PRODUCT_QUANTITY_ONE = 1.0

/**
 * @author yusuf.onder
 * Created on 22.07.2021
 */
class YoProductItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: LayoutYoProductViewBinding by lazy {
    LayoutYoProductViewBinding.inflate(LayoutInflater.from(context), this, true)
  }

  fun bind(
    value: ItemUiModel,
    listener: ItemOperationListener,
    query: String,
    boldEnabled: Boolean = true
  ) {
    val product = value.product

    val productName = SpannableStringBuilder(value.name)
    if (query.isNotEmpty() && boldEnabled) {
      val index = value.name.indexOf(query)
      if (index != INDEX_NOT_FOUND) {
        productName.setSpan(
          StyleSpan(Typeface.BOLD),
          index,
          index + query.length,
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
      }
    }
    binding.tvProductTitle.text = productName
    binding.ivAdd.isInvisible = (product == null).not()
    binding.ivIncreaseOrDelete.isInvisible = product == null
    binding.cvProductQuantity.isInvisible = product == null
    binding.tvProductQuantity.text = product?.quantity
    binding.root.setOnClickListener {
      if (product == null) {
        listener.addProduct(value.name)
      } else {
        listener.increaseQuantity(product)
      }
    }
    binding.ivIncreaseOrDelete.setOnClickListener {
      product?.let {
        if (product.quantityValue <= PRODUCT_QUANTITY_ONE) {
          listener.removeProduct(product)
        } else {
          listener.decreaseQuantity(product)
        }
      }
    }

  }
}
