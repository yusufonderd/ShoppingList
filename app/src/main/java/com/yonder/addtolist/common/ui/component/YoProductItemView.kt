package com.yonder.addtolist.common.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.databinding.LayoutYoProductViewBinding
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.detail.adapter.productlist.IProductOperation

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
    value: ProductEntitySummary,
    listener: IProductOperation,
    product: UserListProductEntity? = null
  ) {
    binding.tvProductTitle.text = value.name
    binding.ivAdd.isInvisible = (product == null).not()
    binding.ivIncreaseOrDelete.isGone = product == null
    binding.cvProductQuantity.isInvisible = (product == null)
    binding.tvProductQuantity.text = "${product?.quantity.orZero().toInt()}"
    binding.root.setSafeOnClickListener {
      if (product == null) {
        listener.addProduct(value)
      } else {
        listener.increaseProductQuantity(product)
      }
    }

    binding.ivIncreaseOrDelete.setSafeOnClickListener {
      product?.let {
        if (product.quantity.orZero() <= PRODUCT_QUANTITY_ONE) {
          listener.removeProductEntity(product)
        } else {
          listener.decreaseProductQuantity(product)
        }
      }
    }

  }
}
